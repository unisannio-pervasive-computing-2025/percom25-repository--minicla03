package minicla03.coinquylife;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.PRESENTATION.UI.ShiftActivity;
import minicla03.coinquylife.Shift.PRESENTATION.ViewModel.ShiftViewModel;

@RunWith(AndroidJUnit4.class)
public class ShiftActivityTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant("android.permission.INTERNET");

    @Mock
    ShiftViewModel shiftViewModel;

    private final MutableLiveData<List<CleaningAssignment>> planningLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> rankResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> houseId = new MutableLiveData<>();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock LiveData
        when(shiftViewModel.getPlanning()).thenReturn(planningLiveData);
        when(shiftViewModel.getRankResult()).thenReturn(rankResult);
        when(shiftViewModel.getIsLoading()).thenReturn(isLoading);
        when(shiftViewModel.getHouseId()).thenReturn(houseId);
    }

    @Test
    public void testFetchPlanningButtonClick_callsViewModelMethod() {
        houseId.setValue("12345");

        doAnswer(invocation -> {
            List<CleaningAssignment> fakeList = new ArrayList<>();
            planningLiveData.postValue(fakeList);
            return null;
        }).when(shiftViewModel).fetchPlanning("12345");

        try (ActivityScenario<ShiftActivity> scenario = ActivityScenario.launch(ShiftActivity.class)) {
            onView(withId(R.id.getPlanning)).perform(click());
        }
    }

    @Test
    public void testProgressBarVisibility_changesWithIsLoading() {
        try (ActivityScenario<ShiftActivity> scenario = ActivityScenario.launch(ShiftActivity.class)) {
            isLoading.postValue(true);
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()));

            isLoading.postValue(false);
            onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)));
        }
    }

    @Test
    public void testRankToastDisplayed_onRankResultUpdate() {
        try (ActivityScenario<ShiftActivity> scenario = ActivityScenario.launch(ShiftActivity.class)) {
            rankResult.postValue("+10 punti");
        }
    }
}
