package com.hzhiping;

import cn.hutool.core.util.StrUtil;
import com.hzhiping.util.RegexUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

/**
 * 文本美化
 *
 * @author hzhiping
 * @date 2023/03/07
 */
public class TextBeautifierAction extends AnAction {

    /**
     * 实现逻辑方法
     *
     * @param action 行为事件
     */
    @Override
    public void actionPerformed(AnActionEvent action) {
        // 根据当前选中的文本进行操作
        Editor editor = action.getData(PlatformDataKeys.EDITOR);
        if (editor == null) return;
        // 获取文档，以便进行后续操作
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        // 为空不做任何处理
        if (StrUtil.isBlank(selectedText)) return;
        Document document = editor.getDocument();
        // 获取文本的开始和结束位置，用新的字符串替换旧的
        WriteCommandAction.runWriteCommandAction(action.getData(PlatformDataKeys.PROJECT), () -> {
            String result = RegexUtil.autoCorrect(selectedText);
            document.replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), result);
        });
    }
}
