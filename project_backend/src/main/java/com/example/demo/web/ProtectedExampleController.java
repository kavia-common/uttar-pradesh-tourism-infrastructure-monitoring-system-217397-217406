package com.example.demo.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Example protected endpoints to demonstrate RBAC.
 */
@RestController
@RequestMapping("/example")
@Tag(name = "Example", description = "Protected example endpoints for RBAC demo")
public class ProtectedExampleController {

    // PUBLIC_INTERFACE
    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin-only endpoint", description = "Accessible only to users with role ADMIN.")
    public String adminOnly() {
        return "Hello, ADMIN!";
    }

    // PUBLIC_INTERFACE
    @GetMapping("/permission-project-read")
    @PreAuthorize("hasAuthority('PROJECT_READ') or hasRole('ADMIN')")
    @Operation(summary = "Permission-gated endpoint", description = "Accessible to users having PROJECT_READ permission or ADMIN role.")
    public String permissionEndpoint() {
        return "You have PROJECT_READ!";
    }
}
