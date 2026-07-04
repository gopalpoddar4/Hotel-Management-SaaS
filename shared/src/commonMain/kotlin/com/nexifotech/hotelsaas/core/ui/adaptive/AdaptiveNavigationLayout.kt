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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
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
import com.nexifotech.hotelsaas.feature.BackupScreen
import com.nexifotech.hotelsaas.feature.BillingScreen
import com.nexifotech.hotelsaas.feature.DashboardScreen
import com.nexifotech.hotelsaas.feature.ExpenseScreen
import com.nexifotech.hotelsaas.feature.FrontOfficeScreen
import com.nexifotech.hotelsaas.feature.GuestManagementScreen
import com.nexifotech.hotelsaas.feature.HouseKeepingScreen
import com.nexifotech.hotelsaas.feature.PayrollScreen
import com.nexifotech.hotelsaas.feature.ReportsScreen
import com.nexifotech.hotelsaas.feature.ReservationsScreen
import com.nexifotech.hotelsaas.feature.RestaurantScreen
import com.nexifotech.hotelsaas.feature.RoomManagementScreen
import com.nexifotech.hotelsaas.feature.SettingsScreen
import com.nexifotech.hotelsaas.feature.UserManagementScreen
import hotelsaas.shared.generated.resources.Res
import hotelsaas.shared.generated.resources.billing
import hotelsaas.shared.generated.resources.dashboard
import hotelsaas.shared.generated.resources.frontoffice
import hotelsaas.shared.generated.resources.guests
import hotelsaas.shared.generated.resources.housekeeping
import hotelsaas.shared.generated.resources.reports
import hotelsaas.shared.generated.resources.reservation
import hotelsaas.shared.generated.resources.resturnt
import hotelsaas.shared.generated.resources.rooms
import hotelsaas.shared.generated.resources.setting
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// ─── Nav Item Model ───────────────────────────────────────────────────────────
private data class NavItem(
    val route: AppRoutes,
    val label: String,
    val emoji: DrawableResource,
)

// ─── Destination Definitions ──────────────────────────────────────────────────
private val primaryNavItems: List<NavItem> = listOf(
    NavItem(AppRoutes.Dashboard,       "Dashboard", Res.drawable.dashboard),
    NavItem(AppRoutes.FrontOffice,     "Front Office", Res.drawable.frontoffice),
    NavItem(AppRoutes.Reservations,    "Reservations", Res.drawable.reservation),
    NavItem(AppRoutes.RoomManagement,  "Rooms",        Res.drawable.rooms),
    NavItem(AppRoutes.GuestManagement, "Guests",       Res.drawable.guests),
    NavItem(AppRoutes.Billing,         "Billing",      Res.drawable.billing),
    NavItem(AppRoutes.Housekeeping,    "Housekeeping", Res.drawable.housekeeping),
    NavItem(AppRoutes.Restaurant,      "Restaurant",   Res.drawable.resturnt),
)

private val secondaryNavItems: List<NavItem> = listOf(
    NavItem(AppRoutes.Reports,           "Reports",  Res.drawable.reports),
    NavItem(AppRoutes.Settings,          "Settings", Res.drawable.setting),
    NavItem(AppRoutes.Payroll,           "Payroll",  Res.drawable.billing),
    NavItem(AppRoutes.Expenses,          "Expenses", Res.drawable.billing),
    NavItem(AppRoutes.UserManagement,    "Users",    Res.drawable.guests),
    NavItem(AppRoutes.BackupAndSecurity, "Backup",   Res.drawable.frontoffice),
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
        Icon(painterResource(item.emoji),"", modifier = Modifier.size(20.dp))
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
            ModalDrawerSheet {
                DrawerHeader()
                Spacer(Modifier.height(8.dp))
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
                                modifier = Modifier.padding(horizontal = 12.dp),
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }

            }
        },
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    compactBottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon     = { NavIcon(item) },
                            label    = { Text(item.label) },
                            selected = currentDestination.isRouteSelected(item),
                            onClick  = { navController.navigateToItem(item) },
                        )
                    }
                    // "More" item opens the drawer to reveal all destinations
                    NavigationBarItem(
                        icon     = { Text("⋯", fontSize = 16.sp) },
                        label    = { Text("More") },
                        selected = false,
                        onClick  = { coroutineScope.launch { drawerState.open() } },
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
        NavigationRail {
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
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }

        }
        MainNavHost(
            navController = navController,
            modifier      = Modifier.weight(1f),
        )
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
            PermanentDrawerSheet(modifier = Modifier.width(240.dp)) {
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
                                modifier = Modifier.padding(horizontal = 12.dp),
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
                                modifier = Modifier.padding(horizontal = 12.dp),
                            )
                        }
                    }
                }

            }
        },
    ) {
        MainNavHost(navController = navController)
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
        composable<AppRoutes.FrontOffice>       { FrontOfficeScreen() }
        composable<AppRoutes.Reservations>      { ReservationsScreen() }
        composable<AppRoutes.RoomManagement>    { RoomManagementScreen() }
        composable<AppRoutes.GuestManagement>   { GuestManagementScreen() }
        composable<AppRoutes.Billing>           { BillingScreen() }
        composable<AppRoutes.Housekeeping>      { HouseKeepingScreen() }
        composable<AppRoutes.Restaurant>        { RestaurantScreen() }
        composable<AppRoutes.Reports>           { ReportsScreen() }
        composable<AppRoutes.Settings>          { SettingsScreen() }
        composable<AppRoutes.Payroll>           { PayrollScreen() }
        composable<AppRoutes.Expenses>          { ExpenseScreen() }
        composable<AppRoutes.UserManagement>    { UserManagementScreen() }
        composable<AppRoutes.BackupAndSecurity> { BackupScreen() }
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