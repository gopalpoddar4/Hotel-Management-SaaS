package com.nexifotech.hotelsaas.core.ui.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Room
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexifotech.hotelsaas.core.navigation.AppRoutes
import com.nexifotech.hotelsaas.feature.backup.presentation.screen.BackupScreen
import com.nexifotech.hotelsaas.feature.backup.presentation.screen.BackupDetailsScreen
import com.nexifotech.hotelsaas.feature.billing.presentation.screen.BillingScreen
import com.nexifotech.hotelsaas.feature.billing.presentation.screen.BillingDetailsScreen
import com.nexifotech.hotelsaas.feature.expenses.presentation.screen.ExpensesScreen
import com.nexifotech.hotelsaas.feature.expenses.presentation.screen.ExpenseDetailsScreen
import com.nexifotech.hotelsaas.feature.frontoffice.presentation.screen.FrontOfficeScreen
import com.nexifotech.hotelsaas.feature.guests.presentation.screen.GuestDetailsScreen
import com.nexifotech.hotelsaas.feature.guests.presentation.screen.GuestsScreen
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.screen.HousekeepingScreen
import com.nexifotech.hotelsaas.feature.housekeeping.presentation.screen.HousekeepingDetailsScreen
import androidx.navigation.toRoute
import com.nexifotech.hotelsaas.feature.payroll.presentation.screen.PayrollScreen
import com.nexifotech.hotelsaas.feature.payroll.presentation.screen.PayrollDetailsScreen
import com.nexifotech.hotelsaas.feature.payroll.presentation.screen.PayslipScreen
import com.nexifotech.hotelsaas.feature.reports.presentation.screen.ReportsScreen
import com.nexifotech.hotelsaas.feature.reports.presentation.screen.ReportDetailsScreen
import com.nexifotech.hotelsaas.feature.reservation.presentation.screen.ReservationScreen
import com.nexifotech.hotelsaas.feature.reservation.presentation.screen.ReservationDetailsScreen

import com.nexifotech.hotelsaas.feature.settings.presentation.screen.SettingsScreen
import com.nexifotech.hotelsaas.feature.rooms.presentation.screen.RoomDetailsScreen
import com.nexifotech.hotelsaas.feature.users.presentation.screen.UserManagementScreen
import com.nexifotech.hotelsaas.feature.users.presentation.screen.UserDetailsScreen
import com.nexifotech.hotelsaas.feature.dashboard.presentation.DashboardScreen
import hotelsaas.shared.generated.resources.Res
import hotelsaas.shared.generated.resources.billing
import hotelsaas.shared.generated.resources.dashboard
import hotelsaas.shared.generated.resources.frontoffice
import hotelsaas.shared.generated.resources.guests
import hotelsaas.shared.generated.resources.housekeeping
import hotelsaas.shared.generated.resources.reports
import hotelsaas.shared.generated.resources.reservation
import com.nexifotech.hotelsaas.feature.restaurant.presentation.screen.RestaurantScreen
import com.nexifotech.hotelsaas.feature.restaurant.presentation.screen.RestaurantOrderDetailsScreen
import hotelsaas.shared.generated.resources.rooms
import hotelsaas.shared.generated.resources.setting
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.nexifotech.hotelsaas.feature.rooms.presentation.screen.RoomsScreen

// ─── Nav Item Model ───────────────────────────────────────────────────────────
private data class NavItem(
    val route: AppRoutes,
    val label: String,
    val emoji: ImageVector,
)

// ─── Destination Definitions ──────────────────────────────────────────────────
private val primaryNavItems: List<NavItem> = listOf(
    NavItem(AppRoutes.Dashboard,       "Dashboard", Icons.Default.Dashboard),
    NavItem(AppRoutes.FrontOffice,     "Front Office", Icons.Default.AccountBox),
    NavItem(AppRoutes.Reservations,    "Reservations", Icons.Default.DateRange),
    NavItem(AppRoutes.RoomManagement,  "Rooms",        Icons.Default.Home),
    NavItem(AppRoutes.GuestManagement, "Guests",       Icons.Default.Person),
    NavItem(AppRoutes.Billing,         "Billing",      Icons.Default.Money),
    NavItem(AppRoutes.Housekeeping,    "Housekeeping", Icons.Default.Build),
    NavItem(AppRoutes.Restaurant,      "Restaurant",   Icons.Default.Restaurant),
)

private val secondaryNavItems: List<NavItem> = listOf(
    NavItem(AppRoutes.Reports,           "Reports",  Icons.AutoMirrored.Filled.List),
    NavItem(AppRoutes.Payroll,           "Payroll",  Icons.Default.CheckCircle),
    NavItem(AppRoutes.Expenses,          "Expenses", Icons.Default.Place),
    NavItem(AppRoutes.UserManagement,    "Users",    Icons.Default.AccountCircle),
    NavItem(AppRoutes.BackupAndSecurity, "Backup",   Icons.Default.Refresh),
    NavItem(AppRoutes.Settings,          "Settings", Icons.Default.Settings)
)

/** First 4 primary items shown in the compact bottom navigation bar. */
private val compactBottomNavItems: List<NavItem> = primaryNavItems.take(4)

// ─── Route Selection Helper ────────────────────────────────────────────────────

private fun NavDestination?.isRouteSelected(item: NavItem): Boolean =
    this?.hierarchy?.any { dest -> dest.hasRoute(item.route::class) } == true

// ─── Emoji Icon Composable ────────────────────────────────────────────────────
@Composable
private fun NavIcon(item: NavItem) {
    Box(
        modifier         = Modifier.size(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(imageVector = item.emoji,"", modifier = Modifier.size(20.dp))
    }
}

// ─── Adaptive Navigation Layout ───────────────────────────────────────────────

@Composable
fun AdaptiveNavigationLayout() {
    val windowSizeClass    = rememberWindowSizeClass()
    val innerNavController = rememberNavController()
    val navBackStackEntry  by innerNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    when (windowSizeClass) {
        WindowSizeClass.Compact  -> CompactLayout(innerNavController, currentDestination)
        WindowSizeClass.Medium   -> MediumLayout(innerNavController, currentDestination)
        WindowSizeClass.Expanded -> ExpandedLayout(innerNavController, currentDestination)
    }
}

// ─── Compact Layout (Mobile — Bottom Nav + Modal Drawer) ──────────────────────

@Composable
private fun CompactLayout(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    val drawerState    = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val allNavItems    = primaryNavItems + secondaryNavItems

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                drawerTonalElevation = 0.dp,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                )
            ) {
                DrawerHeader()
                Spacer(Modifier.height(5.dp))
                LazyColumn {
                    item {
                        allNavItems.forEach { item ->
                            NavigationDrawerItem(
                                icon     = { NavIcon(item) },
                                label    = { Text(item.label) },
                                selected = currentDestination.isRouteSelected(item),
                                onClick  = {
                                    coroutineScope.launch { drawerState.close() }
                                    navController.navigateToItem(item)
                                },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }

            }
        },
    ) {
        Scaffold(
            topBar = {
                GlassTopAppBar(
                    title = currentDestination.getCurrentTitle(allNavItems),
                    onNavigationClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = {
                val dividerColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    tonalElevation = 0.dp,
                    modifier = Modifier.drawBehind {
                        drawLine(
                            color = dividerColor,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                ) {
                    compactBottomNavItems.forEach { item ->
                        val selected = currentDestination.isRouteSelected(item)
                        val animatedBgColor by animateColorAsState(
                            targetValue = if (selected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            label = "bgColor"
                        )
                        val animatedIconColor by animateColorAsState(
                            targetValue = if (selected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            label = "iconColor"
                        )
                        val animatedIconOffset by animateDpAsState(
                            targetValue = if (selected) (-2).dp else 0.dp,
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            label = "iconOffset"
                        )

                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigateToItem(item) },
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(animatedBgColor)
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = item.emoji,
                                        contentDescription = item.label,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .offset(y = animatedIconOffset),
                                        tint = animatedIconColor
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = animatedIconColor,
                                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                                        maxLines = 2,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            },
                            label = null,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = animatedIconColor,
                                unselectedIconColor = animatedIconColor
                            )
                        )
                    }
                    // "More" item opens the drawer to reveal all destinations
                    NavigationBarItem(
                        selected = false,
                        onClick  = { coroutineScope.launch { drawerState.open() } },
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color.Transparent)
                                    .padding(horizontal = 16.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "More",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "More",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Normal,
                                    maxLines = 1,
                                )
                            }
                        },
                        label = null,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            },
        ) { innerPadding ->
            MainNavHost(
                navController = navController,
                modifier      = Modifier.padding(innerPadding),
            )
        }
    }
}

// ─── Medium Layout (Tablet — Navigation Rail) ─────────────────────────────────

@Composable
private fun MediumLayout(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            modifier = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )
        ) {
            LazyColumn {
                item {
                    Spacer(Modifier.height(16.dp))
                    // Primary destinations at the top of the rail
                    primaryNavItems.forEach { item ->
                        NavigationRailItem(
                            icon     = { NavIcon(item) },
                            label    = { Text(item.label) },
                            selected = currentDestination.isRouteSelected(item),
                            onClick  = { navController.navigateToItem(item) },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                    // Secondary destinations at the bottom of the rail
                    secondaryNavItems.forEach { item ->
                        NavigationRailItem(
                            icon     = { NavIcon(item) },
                            label    = { Text(item.label) },
                            selected = currentDestination.isRouteSelected(item),
                            onClick  = { navController.navigateToItem(item) },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

        }
        Column(modifier = Modifier.weight(1f)) {
            val allNavItems = primaryNavItems + secondaryNavItems
            GlassTopAppBar(
                title = currentDestination.getCurrentTitle(allNavItems),
                onNavigationClick = null
            )
            MainNavHost(
                navController = navController,
                modifier      = Modifier.weight(1f)
            )
        }
    }
}

// ─── Expanded Layout (Desktop / Web — Permanent Sidebar) ─────────────────────

@Composable
private fun ExpandedLayout(
    navController: NavHostController,
    currentDestination: NavDestination?,
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                modifier = Modifier
                    .width(240.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                    )
            ) {
                DrawerHeader()
                Spacer(Modifier.height(8.dp))
                LazyColumn {
                    item {
                        SectionLabel("Operations")
                        primaryNavItems.forEach { item ->
                            NavigationDrawerItem(
                                icon     = { NavIcon(item) },
                                label    = { Text(item.label) },
                                selected = currentDestination.isRouteSelected(item),
                                onClick  = { navController.navigateToItem(item) },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        )
                        SectionLabel("Management")
                        secondaryNavItems.forEach { item ->
                            NavigationDrawerItem(
                                icon     = { NavIcon(item) },
                                label    = { Text(item.label) },
                                selected = currentDestination.isRouteSelected(item),
                                onClick  = { navController.navigateToItem(item) },
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }

            }
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            MainNavHost(
                navController = navController,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// ─── Shared Drawer Components ─────────────────────────────────────────────────

@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text  = "Hotel SaaS",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun SectionLabel(label: String) {
    Text(
        text     = label,
        style    = MaterialTheme.typography.labelSmall,
        color    = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 28.dp, vertical = 4.dp),
    )
}

// ─── Inner NavHost — shared graph for all layout variants ─────────────────────

/**
 * Houses all 14 main-app destinations in a single NavHost. Intentionally kept
 * separate from layout chrome so swapping Compact / Medium / Expanded never
 * rebuilds or duplicates the navigation graph.
 */
@Composable
private fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController    = navController,
        startDestination = AppRoutes.Dashboard,
        modifier         = modifier.fillMaxSize(),
    ) {
        composable<AppRoutes.Dashboard>         { DashboardScreen() }
        composable<AppRoutes.FrontOffice>       { 
            FrontOfficeScreen(
                onNavigateToGuestDetails = { guestId ->
                    navController.navigate(AppRoutes.GuestDetails(guestId))
                }
            ) 
        }
        composable<AppRoutes.GuestDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.GuestDetails>()
            GuestDetailsScreen(
                guestId = args.guestId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.ReservationDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.ReservationDetails>()
            ReservationDetailsScreen(
                reservationId = args.reservationId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Reservations>      { 
            ReservationScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(AppRoutes.ReservationDetails(id))
                }
            ) 
        }
        composable<AppRoutes.RoomManagement> { 
            RoomsScreen(
                onNavigateToDetails = { roomId ->
                    navController.navigate(AppRoutes.RoomDetails(roomId))
                }
            ) 
        }
        composable<AppRoutes.RoomDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.RoomDetails>()
            RoomDetailsScreen(
                roomId = args.roomId,
                onBack = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.GuestManagement>   { 
            GuestsScreen(
                onNavigateToDetails = { guestId ->
                    navController.navigate(AppRoutes.GuestDetails(guestId))
                }
            ) 
        }
        composable<AppRoutes.Billing>           { 
            BillingScreen(
                onNavigateToDetails = { invoiceId ->
                    navController.navigate(AppRoutes.BillingDetails(invoiceId))
                }
            ) 
        }
        composable<AppRoutes.BillingDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.BillingDetails>()
            BillingDetailsScreen(
                invoiceId = args.invoiceId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Housekeeping> { 
            HousekeepingScreen(
                onNavigateToDetails = { taskId ->
                    navController.navigate(AppRoutes.HousekeepingDetails(taskId))
                }
            ) 
        }
        composable<AppRoutes.HousekeepingDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.HousekeepingDetails>()
            HousekeepingDetailsScreen(
                taskId = args.taskId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Restaurant>        { 
            RestaurantScreen(
                onNavigateToOrderDetails = { orderId ->
                    navController.navigate(AppRoutes.RestaurantDetails(orderId))
                }
            )
        }
        composable<AppRoutes.RestaurantDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.RestaurantDetails>()
            RestaurantOrderDetailsScreen(
                orderId = args.orderId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Reports>           { 
            ReportsScreen(
                onNavigateToReportDetails = { reportId ->
                    navController.navigate(AppRoutes.ReportDetails(reportId))
                }
            )
        }
        composable<AppRoutes.ReportDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.ReportDetails>()
            ReportDetailsScreen(
                reportId = args.reportId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Settings> { 
            SettingsScreen(
                onNavigateToDetails = { categoryId ->
                    navController.navigate(AppRoutes.SettingsDetails(categoryId))
                }
            ) 
        }
        composable<AppRoutes.SettingsDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.SettingsDetails>()
            com.nexifotech.hotelsaas.feature.settings.presentation.screen.SettingsDetailsScreen(
                categoryId = args.categoryId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Payroll>           { 
            PayrollScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(AppRoutes.PayrollDetails(id))
                },
                onNavigateToPayslip = { id ->
                    navController.navigate(AppRoutes.Payslip(id))
                }
            ) 
        }
        composable<AppRoutes.PayrollDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.PayrollDetails>()
            PayrollDetailsScreen(
                payrollId = args.payrollId,
                onBackClick = { navController.popBackStack() },
                onNavigateToPayslip = { id ->
                    navController.navigate(AppRoutes.Payslip(id))
                }
            )
        }
        composable<AppRoutes.Payslip> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.Payslip>()
            PayslipScreen(
                payrollId = args.payrollId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.Expenses> { 
            ExpensesScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(AppRoutes.ExpenseDetails(id))
                }
            ) 
        }
        composable<AppRoutes.ExpenseDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.ExpenseDetails>()
            ExpenseDetailsScreen(
                expenseId = args.expenseId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.UserManagement>    { 
            UserManagementScreen(
                onNavigateToUserDetails = { userId ->
                    navController.navigate(AppRoutes.UserDetails(userId))
                }
            ) 
        }
        composable<AppRoutes.UserDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.UserDetails>()
            UserDetailsScreen(
                userId = args.userId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<AppRoutes.BackupAndSecurity> { 
            BackupScreen(
                onNavigateToDetails = { id ->
                    navController.navigate(AppRoutes.BackupDetails(id))
                }
            ) 
        }
        composable<AppRoutes.BackupDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<AppRoutes.BackupDetails>()
            BackupDetailsScreen(
                backupId = args.backupId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

// ─── Navigation Helper Extension ─────────────────────────────────────────────

/**
 * Navigates to a [NavItem]'s route using standard tab-navigation back-stack semantics:
 * - Pops back to the graph's start destination, saving state for restoration.
 * - Avoids duplicate instances with [launchSingleTop].
 * - Restores previously saved state via [restoreState].
 */
private fun NavHostController.navigateToItem(item: NavItem) {
    navigate(item.route) {
        popUpTo(graph.findStartDestination().route!!) {
            saveState = true
        }
        launchSingleTop = true
        restoreState    = true
    }
}

// ─── Glass Top App Bar ────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GlassTopAppBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            if (onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
            )
            .shadow(elevation = 2.dp, shape = RectangleShape, clip = false)
    )
}

private fun NavDestination?.getCurrentTitle(allNavItems: List<NavItem>): String {
    return allNavItems.find { item ->
        this?.hierarchy?.any { dest -> dest.hasRoute(item.route::class) } == true
    }?.label ?: "Hotel SaaS"
}