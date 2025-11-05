package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * GeoService provides optional reverse geocoding based on environment configuration.
 * It will gracefully no-op if GEO_REVERSE_ENABLED is false or GEO_API_KEY is missing.
 */
@Service
public class GeoService {

    private static final Logger log = LoggerFactory.getLogger(GeoService.class);

    private final boolean reverseEnabled;
    private final String apiKey;

    public GeoService(
            @Value("${GEO_REVERSE_ENABLED:false}") boolean reverseEnabled,
            @Value("${GEO_API_KEY:}") String apiKey
    ) {
        this.reverseEnabled = reverseEnabled;
        this.apiKey = apiKey == null ? "" : apiKey.trim();
    }

    /**
     * PUBLIC_INTERFACE
     * Attempt reverse geocoding; returns null if disabled/not configured or on failure.
     * This implementation is a stub to keep the system self-contained; integrate with
     * a provider (e.g., Google Maps, Mapbox, LocationIQ) by using apiKey if desired.
     */
    public String reverseGeocode(Double lat, Double lng) {
        if (!reverseEnabled || apiKey.isEmpty() || lat == null || lng == null) {
            return null;
        }
        try {
            // NOTE: No external calls performed; stub returns a friendly string.
            // In production, call the provider with apiKey and lat/lng.
            return "Approximate location near (" + lat + ", " + lng + ")";
        } catch (Exception ex) {
            log.warn("Reverse geocoding failed: {}", ex.getMessage());
            return null;
        }
    }
}
