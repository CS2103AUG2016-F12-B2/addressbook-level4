package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import f12b2.todoapp.model.ToDoApp;
import f12b2.todoapp.model.person.Task;
import f12b2.todoapp.model.util.SampleDataUtil;
import f12b2.todoapp.testutil.TestUtil;

public class SampleDataTest extends ToDoAppGuiTest {
    @Override
    protected ToDoApp getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void toDoApp_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
