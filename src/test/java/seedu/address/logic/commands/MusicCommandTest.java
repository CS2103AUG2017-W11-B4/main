package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.MusicCommand.MESSAGE_USAGE;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.MainWindowHandle;
import seedu.address.TestApp;
import systemtests.SystemTestSetupHelper;

//@@author hanselblack
public class MusicCommandTest {

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initializeStage();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication();
        mainWindowHandle = setupHelper.setupMainWindowHandle();
    }

    @Test
    public void execute_music_wrongGenre() {
        MusicCommand musicCommand = new MusicCommand("play", "nonExistedGenre");
        CommandResult commandResult = musicCommand.execute();
        assertEquals(MESSAGE_USAGE, commandResult.feedbackToUser);
    }
    @Test
    public void equals() {

        MusicCommand musicFirstCommand = new MusicCommand("play");

        // same object -> returns true
        assertTrue(musicFirstCommand.equals(musicFirstCommand));

        // same values -> returns true
        MusicCommand emailFirstCommandCopy = new MusicCommand("play");
        assertTrue(musicFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(musicFirstCommand.equals(1));

        // null -> returns false
        assertFalse(musicFirstCommand.equals(null));
    }
}
