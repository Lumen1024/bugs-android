package com.lumen.bugs_android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.lumen.bugs_android.navigation.AppBottomBar
import com.lumen.bugs_android.navigation.BottomTab
import com.lumen.bugs_android.screen.authors.AuthorsScreenRoot
import com.lumen.bugs_android.screen.game.GameScreenRoot
import com.lumen.bugs_android.screen.create_profile.CreateProfileScreenRoot
import com.lumen.bugs_android.screen.rules.RulesScreenRoot
import com.lumen.bugs_android.screen.settings.SettingsScreenRoot
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { BottomTab.entries.size })
    val scope = rememberCoroutineScope()

    fun selectTab(tab: BottomTab) {
        scope.launch { pagerState.animateScrollToPage(tab.ordinal) }
    }

    val selectedTab = BottomTab.entries[pagerState.currentPage]

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                selectedTab = selectedTab,
                onSelectTab = ::selectTab,
            )
        },
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) { page ->
            when (BottomTab.entries[page]) {
                BottomTab.Auth -> CreateProfileScreenRoot(
                    onContinue = { selectTab(BottomTab.Game) },
                )

                BottomTab.Authors -> AuthorsScreenRoot()
                BottomTab.Game -> GameScreenRoot()

                BottomTab.Rules -> RulesScreenRoot()
                BottomTab.Settings -> SettingsScreenRoot()
            }
        }
    }
}
