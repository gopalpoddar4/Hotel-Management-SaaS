package com.nexifotech.hotelsaas.feature.settings.data.repository

import com.nexifotech.hotelsaas.feature.settings.domain.model.*
import com.nexifotech.hotelsaas.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay

class DummySettingsRepository : SettingsRepository {

    private val initialSettings = AppSettings(
        hotelProfile = HotelProfileSettings(
            hotelName = "Grand Plaza Resort",
            address = "123 Ocean Drive, Azure Coast, AC 90210",
            contactEmail = "admin@grandplaza.com",
            contactPhone = "+1 (555) 123-4567",
            logoUrl = "https://example.com/logo.png",
            timeZone = "UTC-5 (Eastern Time)",
            businessHours = "24/7"
        ),
        general = GeneralSettings(
            dateFormat = "DD/MM/YYYY",
            timeFormat = "12-hour",
            language = "English (US)",
            currency = "USD ($)",
            numberFormat = "1,000.00"
        ),
        appearance = AppearanceSettings(
            theme = "System Default",
            accentColor = "#0052CC",
            fontSize = "Medium",
            compactMode = false,
            dashboardLayout = "Standard"
        ),
        billing = BillingSettings(
            taxConfiguration = "Inclusive",
            gstNumber = "GST-987654321",
            invoicePrefix = "INV-",
            invoiceFooter = "Thank you for your stay!",
            paymentDefaults = "Credit Card"
        ),
        reservation = ReservationSettings(
            defaultCheckInTime = "14:00",
            defaultCheckOutTime = "11:00",
            bookingRules = "Standard",
            cancellationRules = "24 Hours Prior",
            advanceBookingLimits = "365 Days"
        ),
        notification = NotificationSettings(
            emailNotificationsEnabled = true,
            smsNotificationsEnabled = true,
            pushNotificationsEnabled = false,
            reminderSettings = "24 hours before check-in"
        ),
        emailSms = EmailSmsSettings(
            smtpConfiguration = "smtp.grandplaza.com",
            senderName = "Grand Plaza Reservations",
            emailTemplatesConfigured = 12,
            smsTemplatesConfigured = 5
        ),
        printer = PrinterSettings(
            receiptPrinter = "Epson TM-T88VI (Front Desk)",
            invoicePrinter = "HP LaserJet Pro (Back Office)",
            kitchenPrinter = "Star Micronics SP742 (Kitchen)"
        ),
        security = SecuritySettings(
            passwordPolicy = "Strong (8+ chars, numbers, symbols)",
            sessionTimeoutMinutes = 30,
            twoFactorAuthEnabled = true,
            maxLoginAttempts = 5,
            deviceManagementEnabled = true
        ),
        backup = BackupSettings(
            autoBackupEnabled = true,
            backupFrequency = "Daily",
            backupLocation = "AWS S3 (US-East-1)",
            backupRetentionDays = 30
        ),
        systemInformation = SystemInformation(
            applicationVersion = "v2.5.4",
            buildNumber = "4512",
            databaseVersion = "PostgreSQL 14.5",
            lastUpdate = "2023-10-15 03:00 UTC",
            serverStatus = "Online - All Systems Nominal",
            platform = "Kotlin Multiplatform"
        ),
        aboutApplication = AboutApplication(
            saasVersion = "Enterprise Edition",
            copyright = "© 2024 Nexifotech. All rights reserved.",
            developer = "Nexifotech Solutions",
            license = "Commercial License",
            privacyPolicyUrl = "https://example.com/privacy",
            termsUrl = "https://example.com/terms"
        ),
        dashboardMetrics = DashboardMetrics(
            activeUsers = 12,
            connectedDevices = 34,
            storageUsedGB = 45.2,
            securityStatus = "Optimal"
        )
    )

    private val _settingsFlow = MutableStateFlow(initialSettings)

    override fun getSettings(): Flow<AppSettings> {
        return _settingsFlow.asStateFlow()
    }

    override suspend fun saveSettings(settings: AppSettings) {
        delay(500) // Simulate network call
        _settingsFlow.value = settings
    }

    override suspend fun resetSettings() {
        delay(500) // Simulate network call
        _settingsFlow.value = initialSettings
    }
}
