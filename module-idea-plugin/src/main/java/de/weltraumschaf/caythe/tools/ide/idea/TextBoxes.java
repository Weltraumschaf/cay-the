package de.weltraumschaf.caythe.tools.ide.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public final class TextBoxes extends AnAction {
    public TextBoxes() {
        // Set the menu item name.
        super("Text _Boxes");
    }

    public void actionPerformed(final AnActionEvent event) {
        final Project project = event.getData(PlatformDataKeys.PROJECT);
        final String txt= Messages.showInputDialog(project,
            "What is your name?",
            "Input your name",
            Messages.getQuestionIcon());

        Messages.showMessageDialog(
            project,
            "Hello, " + txt + "!\n I am glad to see you.",
            "Information",
            Messages.getInformationIcon());
    }
}
