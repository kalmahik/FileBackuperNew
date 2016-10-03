package ru.levelp;

import ru.levelp.presenter.ConsolePresenter;
import ru.levelp.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsolePresenter presenter = new ConsolePresenter();
        ConsoleView view = new ConsoleView(presenter);
        presenter.setView(view);
        presenter.start();
    }

    /*
    1) uploadBackup протестировать
    2) Просмотреть и разобраться во всем коде
    3) Пройтись по всем недосказанным else-ам
        и обеспечить ответы пользователю в случае ошибки
     */
}
