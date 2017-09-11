'use strict';
import * as vscode from 'vscode';

/**
 * Activates the extension.
 *
 * @param context must not be {@code null}
 */
export function activate(context: vscode.ExtensionContext) {
    console.log('Congratulations, your extension "caythe" is now active!');

    let sayHello = vscode.commands.registerCommand('extension.sayHello', () => {
        var editor = vscode.window.activeTextEditor;
        if (!editor) {
            return; // No open text editor
        }

        var selection = editor.selection;
        var text = editor.document.getText(selection);

        // Display a message box to the user
        vscode.window.showInformationMessage('Selected characters: ' + text.length);
    });

    context.subscriptions.push(sayHello);

    let compile = vscode.commands.registerCommand('extension.compile', () => {
        vscode.window.showInformationMessage('compile sources...');
    });

    context.subscriptions.push(compile);

    let run = vscode.commands.registerCommand('extension.run', () => {
        vscode.window.showInformationMessage('Run program');
    });

    context.subscriptions.push(run);
}

/**
 * Deactivate the extension.
 */
export function deactivate() {
}