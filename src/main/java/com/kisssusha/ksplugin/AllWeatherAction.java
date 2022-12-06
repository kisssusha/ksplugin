package com.kisssusha.ksplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class AllWeatherAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Weather form = new Weather();
        form.setSize(400, 400);
    }
}
