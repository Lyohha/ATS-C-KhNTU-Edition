package ua.lyohha.tasks.operation.assignmentoperation;

import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;
import ua.lyohha.tasks.Task;

public class AssignmentOperationsTask implements Task {
    private String taskName = "Операции присваивания";
    TemplateGenerator templateGenerator;

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
    public String[] checkAnswers(@NotNull  String[] answers) {
        return templateGenerator.getAnswers();
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskCode(@NotNull  Pane pane) {
        templateGenerator.setTemplate(pane);
    }
}
