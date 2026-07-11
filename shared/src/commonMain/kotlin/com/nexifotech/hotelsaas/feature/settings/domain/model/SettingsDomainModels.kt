package com.nexifotech.hotelsaas.feature.settings.domain.model

data class HotelProfileSettings(
    val hotelName: String,
    val address: String,
    val contactEmail: String,
    val contactPhone: String,
    val logoUrl: String,
    val timeZone: String,
    val businessHours: String
)

data class GeneralSettings(
    val dateFormat: String,
    val timeFormat: String,
    val language: String,
    val currency: String,
    val numberFormat: String
)

data class AppearanceSettings(
    val theme: String,
    val accentColor: String,
    val fontSize: String,
    val compactMode: Boolean,
    val dashboardLayout: String
)

data class BillingSettings(
    val taxConfiguration: String,
    val gstNumber: String,
    val invoicePrefix: String,
    val invoiceFooter: String,
    val paymentDefaults: String
)

data class ReservationSettings(
    val defaultCheckInTime: String,
    val defaultCheckOutTime: String,
    val bookingRules: String,
    val cancellationRules: String,
    val advanceBookingLimits: String
)

data class NotificationSettings(
    val emailNotificationsEnabled: Boolean,
    val smsNotificationsEnabled: Boolean,
    val pushNotificationsEnabled: Boolean,
    val reminderSettings: String
)

data class EmailSmsSettings(
    val smtpConfiguration: String,
    val senderName: String,
    val emailTemplatesConfigured: Int,
    val smsTemplatesConfigured: Int
)

data class PrinterSettings(
    val receiptPrinter: String,
    val invoicePrinter: String,
    val kitchenPrinter: String
)

data class SecuritySettings(
    val passwordPolicy: String,
    val sessionTimeoutMinutes: Int,
    val twoFactorAuthEnabled: Boolean,
    val maxLoginAttempts: Int,
    val deviceManagementEnabled: Boolean
)

data class BackupSettings(
    val autoBackupEnabled: Boolean,
    val backupFrequency: String,
    val backupLocation: String,
    val backupRetentionDays: Int
)

data class SystemInformation(
    val applicationVersion: String,
    val buildNumber: String,
    val databaseVersion: String,
    val lastUpdate: String,
    val serverStatus: String,
    val platform: String
)

data class AboutApplication(
    val saasVersion: String,
    val copyright: String,
    val developer: String,
    val license: String,
    val privacyPolicyUrl: String,
    val termsUrl: String
)

data class DashboardMetrics(
    val activeUsers: Int,
    val connectedDevices: Int,
    val storageUsedGB: Double,
    val securityStatus: String
)

data class AppSettings(
    val hotelProfile: HotelProfileSettings,
    val general: GeneralSettings,
    val appearance: AppearanceSettings,
    val billing: BillingSettings,
    val reservation: ReservationSettings,
    val notification: NotificationSettings,
    val emailSms: EmailSmsSettings,
    val printer: PrinterSettings,
    val security: SecuritySettings,
    val backup: BackupSettings,
    val systemInformation: SystemInformation,
    val aboutApplication: AboutApplication,
    val dashboardMetrics: DashboardMetrics
)
