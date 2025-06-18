package minicla03.coinquylife;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;

import android.app.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.DOMAIN.useCase.IGetPlanningUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.ITaskDoneUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.IToRankUseCase;
import minicla03.coinquylife.Shift.PRESENTATION.ViewModel.ShiftViewModel;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ShiftViewModelTest {

    @Mock
    IGetPlanningUseCase getPlanningUseCase;

    @Mock
    ITaskDoneUseCase taskDoneUseCase;

    @Mock
    IToRankUseCase toRankUseCase;

    @Mock
    Application mockApplication;

    ShiftViewModel shiftViewModel;

    @Before
    public void setup() {
        shiftViewModel = new ShiftViewModel(mockApplication);
    }

    @Test
    public void testConfirmTaskCompletion_Success() {
        String idShift = "shift123";
        String tipoCompito = "Pulizia";
        String username = "mario";
        String houseId = "house001";
        String endTime = "2025-06-20T10:00";

        doAnswer(invocation -> {
            ((Callback<String>) invocation.getArgument(1)).onResponse(null, Response.success("task_done"));
            return null;
        }).when(taskDoneUseCase).execute(eq(idShift), any(), any());

        doAnswer(invocation -> {
            ((Callback<String>) invocation.getArgument(4)).onResponse(null, Response.success("task_done"));
            return null;
        }).when(toRankUseCase).execute(eq(tipoCompito), eq(username), eq(houseId), eq(endTime), any());

        shiftViewModel.confirmTaskCompletion("fakeToken", idShift, tipoCompito, username, houseId, endTime);

        assertEquals("task_done", shiftViewModel.getTaskDoneResult().getValue());
        assertEquals("task_done", shiftViewModel.getRankResult().getValue());
    }

    @Test
    public void testFetchPlanning_EmptyResult_ShouldNotCrash() {
        doAnswer(invocation -> {
            ((Callback<List<CleaningAssignment>>) invocation.getArgument(2)).onResponse(null, Response.success(new ArrayList<>()));
            return null;
        }).when(getPlanningUseCase).execute(eq("house001"), any(), any(), any());

        shiftViewModel.fetchPlanning("house001");

        assertTrue(shiftViewModel.getPlanning().getValue() == null || shiftViewModel.getPlanning().getValue().isEmpty());
    }
}