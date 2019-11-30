package ua.lyohha.page.menucontrols;

//Управление

import ua.lyohha.page.MenuControl;
import ua.lyohha.page.menu.MenuPage;
import ua.lyohha.page.testing.TestingPage;
import ua.lyohha.tasks.management.ifeleseconditional.IfElseConditionalTask;

public class ManagementMenuControls extends MenuControl {
    public ManagementMenuControls(MenuPage menuPage) {
        super(menuPage);
    }

    @Override
    public void onItemSelected(int item) {
        switch (item) {
            case 0:
                ((TestingPage)menuPage.navigation.navigateTo(TestingPage.class)).setTask(new IfElseConditionalTask());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                menuPage.navigation.navigateBack();
                break;
        }
    }

    @Override
    public String[] getItems() {
        return new String[]{
                "Условный оператор if-else",
                "Операторы цикла while и do-while",
                "Оператор цикла for",
                "Переключатель (оператор switch)",
                "Вложенность операторов",
                "Назад"};
    }

    @Override
    public String getMenuName() {
        return "Управление";
    }
}
