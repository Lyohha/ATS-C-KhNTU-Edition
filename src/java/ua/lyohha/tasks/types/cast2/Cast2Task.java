package ua.lyohha.tasks.types.cast2;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;
import ua.lyohha.language.Language;
import ua.lyohha.tasks.Task;

public class Cast2Task implements Task {
    private String taskName = Language.getLocalized("types_menu.item2.name");
    private TemplateGenerator templateGenerator;

    @Override
    public void create() {
        templateGenerator = new TemplateGenerator();
    }

    @Override
    public int getCountAnswers() {
        return 4;
    }

    @Override
    public String[] getAnswers() {
        return templateGenerator.getAnswers();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskCode(@NotNull Pane pane) {
        templateGenerator.setTemplate(pane);
    }
}
