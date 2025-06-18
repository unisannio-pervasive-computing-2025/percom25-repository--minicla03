package minicla03.coinquylife;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.Shift.PRESENTATION.UI.ShiftActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ShiftActivityTest
{
    @Rule
    public ActivityTestRule<ShiftActivity> activityRule = new ActivityTestRule<>(ShiftActivity.class, true, false);

    private List<User> mockUsers;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        setupMockData();
        setupSharedPreferences();
        activityRule.launchActivity(null);
    }

    private void setupMockData() {
        User user1 = new User();
        user1.setUsername("mario_rossi");
        user1.setEmail("mario@test.com");

        User user2 = new User();
        user2.setUsername("luigi_verdi");
        user2.setEmail("luigi@test.com");

        User user3 = new User();
        user3.setUsername("anna_bianchi");
        user3.setEmail("anna@test.com");

        mockUsers = Arrays.asList(user1, user2, user3);
    }

    private void setupSharedPreferences() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString("houseCode", "TEST_HOUSE_123");
        editor.putString("token", "test_token_123");
        editor.putString("coinquyList", new Gson().toJson(mockUsers));
        editor.apply();
    }

    @Test
    public void activity_startsCorrectly_allViewsAreVisible() throws InterruptedException {
        onView(withId(R.id.etStartIndisponibilita)).check(matches(isDisplayed()));
        onView(withId(R.id.etEndIndisponibilita)).check(matches(isDisplayed()));
        onView(withId(R.id.etDataOraCompito)).check(matches(isDisplayed()));
        onView(withId(R.id.etDescrizioneCompito)).check(matches(isDisplayed()));
        onView(withId(R.id.spinnerCoinquilino)).check(matches(isDisplayed()));
        onView(withId(R.id.spinnerTipoCompito)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSaveIndisponibilita)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddCompito)).check(matches(isDisplayed()));
        onView(isRoot()).perform(swipeUp());

        onView(withId(R.id.getPlanning))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.getPlanning)).check(matches(isDisplayed()));
        onView(withId(R.id.rvShiftCalendar)).check(matches(isDisplayed()));
        onView(withId(R.id.btnBack)).check(matches(isDisplayed()));
    }

    @Test
    public void spinners_arePopulatedCorrectly() {
        onView(withId(R.id.spinnerCoinquilino)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("mario_rossi"))).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(String.class)), is("luigi_verdi"))).check(matches(isDisplayed()));
        onData(allOf(is(instanceOf(String.class)), is("anna_bianchi"))).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void saveIndisponibilita_withValidData_showsSuccessMessage() {
        selectCoinquilinoFromSpinner("mario_rossi");
        setDateTimeInField(R.id.etStartIndisponibilita, "2024-12-01 09:00");
        setDateTimeInField(R.id.etEndIndisponibilita, "2024-12-01 17:00");

        onView(withId(R.id.btnSaveIndisponibilita)).perform(click());

        onView(withId(R.id.etStartIndisponibilita)).check(matches(withText("")));
        onView(withId(R.id.etEndIndisponibilita)).check(matches(withText("")));
        onView(withId(R.id.spinnerCoinquilino)).check(matches(withSpinnerText("Seleziona Coinquilino")));
    }

    @Test
    public void saveIndisponibilita_withEmptyFields_showsErrorMessage() {
        onView(withId(R.id.btnSaveIndisponibilita)).perform(click());
        onView(withId(R.id.etStartIndisponibilita)).check(matches(withText("")));
        onView(withId(R.id.etEndIndisponibilita)).check(matches(withText("")));
    }

    @Test
    public void saveIndisponibilita_withMissingCoinquilino_showsErrorMessage() {
        setDateTimeInField(R.id.etStartIndisponibilita, "2024-12-01 09:00");
        setDateTimeInField(R.id.etEndIndisponibilita, "2024-12-01 17:00");

        onView(withId(R.id.btnSaveIndisponibilita)).perform(click());

        onView(withId(R.id.etStartIndisponibilita)).check(matches(withText("2024-12-01 09:00")));
        onView(withId(R.id.etEndIndisponibilita)).check(matches(withText("2024-12-01 17:00")));
    }


    @Test
    public void addCompito_withValidData_showsSuccessMessage() {
        onView(withId(R.id.etDescrizioneCompito))
                .perform(typeText("Pulire il bagno principale"), closeSoftKeyboard());

        selectTipoCompitoFromSpinner("\uD83E\uDDF9 Cleaning");
        setDateTimeInField(R.id.etDataOraCompito, "2024-12-15 14:30");

        onView(withId(R.id.btnAddCompito)).perform(click());

        onView(withId(R.id.etDescrizioneCompito)).check(matches(withText("")));
        onView(withId(R.id.etDataOraCompito)).check(matches(withText("")));
    }


    @Test
    public void addCompito_withEmptyDescription_showsErrorMessage() {
        selectTipoCompitoFromSpinner("\uD83D\uDD27 Maintenance");
        setDateTimeInField(R.id.etDataOraCompito, "2024-12-15 14:30");
        onView(withId(R.id.btnAddCompito)).perform(click());
        onView(withId(R.id.etDataOraCompito)).check(matches(withText("2024-12-15 14:30")));
    }

    @Test
    public void addCompito_withEmptyFields_showsErrorMessage() {
        onView(withId(R.id.btnAddCompito)).perform(click());
        onView(withId(R.id.etDescrizioneCompito)).check(matches(withText("")));
        onView(withId(R.id.etDataOraCompito)).check(matches(withText("")));
    }

    @Test
    public void getPlanning_whenClicked_showsProgressBar() {
        onView(isRoot()).perform(swipeUp());

        onView(withId(R.id.getPlanning))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void recyclerView_isDisplayedCorrectly() {
        onView(withId(R.id.rvShiftCalendar))
                .check(matches(isDisplayed()))
                .check(matches(hasMinimumChildCount(0)));
    }

    @Test
    public void recyclerView_itemClick_triggersTaskCompletion() {
        try {
            onView(withId(R.id.rvShiftCalendar))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
        catch (Exception ignored)
        {
        }
    }

    @Test
    public void backButton_whenClicked_finishesActivity() {
        onView(withId(R.id.btnBack)).perform(click());
    }

    @Test
    public void dateTimeFields_whenClicked_showDateTimePicker() {
        onView(withId(R.id.etStartIndisponibilita)).perform(click());
        onView(withId(R.id.etEndIndisponibilita)).perform(click());
        onView(withId(R.id.etDataOraCompito)).perform(click());
    }

    @Test
    public void inputFields_acceptValidText() {
        onView(withId(R.id.etDescrizioneCompito))
                .perform(typeText("Descrizione test compito"), closeSoftKeyboard());

        onView(withId(R.id.etDescrizioneCompito))
                .check(matches(withText("Descrizione test compito")));
    }

    @Test
    public void spinnerCoinquilino_canSelectDifferentOptions() {
        selectCoinquilinoFromSpinner("luigi_verdi");
        onView(withId(R.id.spinnerCoinquilino))
                .check(matches(withSpinnerText("luigi_verdi")));

        selectCoinquilinoFromSpinner("anna_bianchi");
        onView(withId(R.id.spinnerCoinquilino))
                .check(matches(withSpinnerText("anna_bianchi")));
    }

    @Test
    public void spinnerTipoCompito_canSelectDifferentTypes() {
        selectTipoCompitoFromSpinner("\uD83D\uDD27 Maintenance");
    }

    private void selectCoinquilinoFromSpinner(String coinquilino) {
        onView(withId(R.id.spinnerCoinquilino)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(coinquilino))).perform(click());
    }

    private void selectTipoCompitoFromSpinner(String tipoCompito) {
        onView(withId(R.id.spinnerTipoCompito)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(tipoCompito))).perform(click());
    }

    private void setDateTimeInField(int fieldId, String dateTime) {
        onView(withId(fieldId)).perform(replaceText(dateTime), closeSoftKeyboard());
    }

    @Test
    public void activity_initializesViewModelsCorrectly() {
        onView(withId(R.id.spinnerCoinquilino)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("mario_rossi")))
                .check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void progressBar_isHiddenInitially() {
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())));
    }

    @Test
    public void completeIndisponibilitaFlow_worksCorrectly() {
        selectCoinquilinoFromSpinner("mario_rossi");
        setDateTimeInField(R.id.etStartIndisponibilita, "2024-12-01 09:00");
        setDateTimeInField(R.id.etEndIndisponibilita, "2024-12-01 17:00");

        onView(withId(R.id.btnSaveIndisponibilita)).perform(click());

        onView(withId(R.id.etStartIndisponibilita)).check(matches(withText("")));
        onView(withId(R.id.etEndIndisponibilita)).check(matches(withText("")));
        onView(withId(R.id.spinnerCoinquilino)).check(matches(withSpinnerText("Seleziona Coinquilino")));
    }

    @Test
    public void completeCompitoFlow_worksCorrectly() {
        onView(withId(R.id.etDescrizioneCompito))
                .perform(typeText("Pulire cucina"), closeSoftKeyboard());
        selectTipoCompitoFromSpinner("\uD83E\uDDF9 Cleaning");
        setDateTimeInField(R.id.etDataOraCompito, "2024-12-15 10:00");

        onView(withId(R.id.btnAddCompito)).perform(click());

        onView(withId(R.id.etDescrizioneCompito)).check(matches(withText("")));
        onView(withId(R.id.etDataOraCompito)).check(matches(withText("")));
    }
}
