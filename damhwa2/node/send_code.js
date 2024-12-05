const express = require('express');
const mysql = require('mysql');
const bodyParser = require('body-parser');
const WebSocket = require('ws');
const http = require('http');

// Express 애플리케이션 설정
const app = express();
const HTTP_PORT = 3000; // HTTP 서버 포트 설정

// MySQL 데이터베이스 연결 설정
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'damhwa'
});

// Connect to the database
db.connect((err) => {
    if (err) {
        console.error('Unable to connect to database:', err);
    } else {
        console.log('Connected to MySQL database');
    }
});

// Middleware to parse request bodies
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Route to handle sending the verification code
app.post('/send_code', (req, res) => {
    const phoneNumber = req.body.phoneNumber;
    const verificationCode = Math.floor(100000 + Math.random() * 900000); // 6자리 인증 코드 생성

    // 데이터베이스에 코드 삽입 또는 업데이트
    db.query('SELECT * FROM phone_verifications WHERE phone_number = ?', [phoneNumber], (err, results) => {
        if (err) {
            res.status(500).send('Database error');
        } else if (results.length > 0) {
            // 이미 존재하는 경우 업데이트
            db.query('UPDATE phone_verifications SET verification_code = ?, created_at = NOW(), is_verified = FALSE WHERE phone_number = ?',
            [verificationCode, phoneNumber], (updateErr) => {
                if (updateErr) {
                    res.status(500).send('Error updating verification code');
                } else {
                    // 코드 성공적으로 업데이트됨
                    res.send(String(verificationCode));
                    // 실제로는 SMS 서비스 등을 이용해 사용자에게 코드 전송
                }
            });
        } else {
            // 존재하지 않는 경우 새로 삽입
            db.query('INSERT INTO phone_verifications (phone_number, verification_code) VALUES (?, ?)',
            [phoneNumber, verificationCode], (insertErr) => {
                if (insertErr) {
                    res.status(500).send('Error storing verification code');
                } else {
                    // 코드 성공적으로 삽입됨
                    res.send(String(verificationCode));
                    // 실제로는 SMS 서비스 등을 이용해 사용자에게 코드 전송
                }
            });
        }
    });
});


// Route to verify the code
app.post('/phone_verifications', (req, res) => {
    const phoneNumber = req.body.phoneNumber;
    const code = req.body.code;

    if (!phoneNumber || !code) {
        return res.status(400).json({ error: 'Phone number and code are required' });
    }

    // Check the verification code in the database
    const query = 'SELECT * FROM phone_verifications WHERE phone_number = ? AND verification_code = ?';
    db.query(query, [phoneNumber, code], (err, results) => {
        if (err) {
            return res.status(500).json({ error: 'Database error', details: err.message });
        }

        if (results.length === 0) {
            return res.status(400).json({ error: 'Invalid verification code' });
        }

        // Update the verified status in the database
        const updateQuery = 'UPDATE phone_verifications SET verified = TRUE WHERE phone_number = ? AND verification_code = ?';
        db.query(updateQuery, [phoneNumber, code], (err, updateResult) => {
            if (err) {
                return res.status(500).json({ error: 'Database error', details: err.message });
            }

            return res.json({ message: 'Verification successful' });
        });
    });
});

app.post('/verify_code', (req, res) => {
    const phoneNumber = req.body.phoneNumber;
    const code = req.body.code;

    // 데이터베이스에서 코드 확인
    db.query('SELECT * FROM phone_verifications WHERE phone_number = ? AND verification_code = ?', [phoneNumber, code], (err, results) => {
        if (err) {
            res.status(500).send('Database error');
        } else if (results.length > 0) {
            // 코드 일치 - 인증 성공, is_verified 필드 업데이트
            db.query('UPDATE phone_verifications SET is_verified = TRUE WHERE phone_number = ?', [phoneNumber], (updateErr) => {
                if (updateErr) {
                    res.status(500).send('Error updating verification status');
                } else {
                    res.send('Verification successful');
                }
            });
        } else {
            // 코드 불일치 - 인증 실패
            res.status(400).send('Invalid verification code');
        }
    });
});

const users = [
    { id: 1, name: "User1" },
    { id: 2, name: "User2" },
    { id: 3, name: "User3" },
    { id: 4, name: "User4" }
];

// 랜덤 매칭 API
app.get('/api/random-matching', (req, res) => {
    const randomIndex = Math.floor(Math.random() * users.length);
    const matchedUser = users[randomIndex];
    res.json({ success: true, matchedUser });
});

// HTTP 서버 시작
app.listen(HTTP_PORT, () => {
    console.log(`HTTP Server is running on http://localhost:${HTTP_PORT}`);
});

// WebSocket 서버 설정
const WS_PORT = 8080; // WebSocket 서버 포트 설정
const wss = new WebSocket.Server({ port: WS_PORT });

// WebSocket 연결 관리
wss.on('connection', (ws) => {
    console.log('WebSocket connection established');

    // 클라이언트 메시지 수신
    ws.on('message', (message) => {
        console.log('received: %s', message);

        // 메시지 파싱 (예: { from: 'User1', to: 'User2', message: 'Hello' })
        let parsedMessage;
        try {
            parsedMessage = JSON.parse(message);
        } catch (e) {
            console.error('Invalid message format');
            return;
        }

        // 발신자와 수신자 정보로 메시지를 처리
        const { from, to, text } = parsedMessage;

        // 클라이언트에게 메시지 전송 (발신자와 수신자 정보를 포함)
        wss.clients.forEach((client) => {
            if (client.readyState === WebSocket.OPEN) {
                // 여기서 발신자와 수신자에 맞는 처리
                if (client !== ws) {  // 자신에게 보내지 않도록 처리
                    client.send(JSON.stringify({ from, to, text }));
                }
            }
        });
    });

    // 클라이언트 초기 메시지 전송
    // ws.send(JSON.stringify({ from: 'Server', text: 'Welcome to the WebSocket server!' }));
});

console.log(`WebSocket Server is running on ws://localhost:${WS_PORT}`);