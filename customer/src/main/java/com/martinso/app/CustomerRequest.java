package com.martinso.app;

public record CustomerRequest(
        String firstname,
        String lastname,
        String email
) {
}
