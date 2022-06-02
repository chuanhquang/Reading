package com.example.quangca.request;

public record ChangePasswordForm(

        String oldPassword,

        String newPassword
) {
}
