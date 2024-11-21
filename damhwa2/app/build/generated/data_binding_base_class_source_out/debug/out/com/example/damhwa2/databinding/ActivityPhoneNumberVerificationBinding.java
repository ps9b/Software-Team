// Generated by view binder compiler. Do not edit!
package com.example.damhwa2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.damhwa2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPhoneNumberVerificationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button buttonSendCode;

  @NonNull
  public final Button buttonVerifyCode;

  @NonNull
  public final EditText editTextPhoneNumber;

  @NonNull
  public final EditText editTextVerificationCode;

  private ActivityPhoneNumberVerificationBinding(@NonNull LinearLayout rootView,
      @NonNull Button buttonSendCode, @NonNull Button buttonVerifyCode,
      @NonNull EditText editTextPhoneNumber, @NonNull EditText editTextVerificationCode) {
    this.rootView = rootView;
    this.buttonSendCode = buttonSendCode;
    this.buttonVerifyCode = buttonVerifyCode;
    this.editTextPhoneNumber = editTextPhoneNumber;
    this.editTextVerificationCode = editTextVerificationCode;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPhoneNumberVerificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPhoneNumberVerificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_phone_number_verification, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPhoneNumberVerificationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonSendCode;
      Button buttonSendCode = ViewBindings.findChildViewById(rootView, id);
      if (buttonSendCode == null) {
        break missingId;
      }

      id = R.id.buttonVerifyCode;
      Button buttonVerifyCode = ViewBindings.findChildViewById(rootView, id);
      if (buttonVerifyCode == null) {
        break missingId;
      }

      id = R.id.editTextPhoneNumber;
      EditText editTextPhoneNumber = ViewBindings.findChildViewById(rootView, id);
      if (editTextPhoneNumber == null) {
        break missingId;
      }

      id = R.id.editTextVerificationCode;
      EditText editTextVerificationCode = ViewBindings.findChildViewById(rootView, id);
      if (editTextVerificationCode == null) {
        break missingId;
      }

      return new ActivityPhoneNumberVerificationBinding((LinearLayout) rootView, buttonSendCode,
          buttonVerifyCode, editTextPhoneNumber, editTextVerificationCode);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
