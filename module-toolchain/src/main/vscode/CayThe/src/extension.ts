'use strict';
import * as vscode from 'vscode';

/**
 * Activates the extension.
 *
 * @param context must not be {@code null}
 */
export function activate(context: vscode.ExtensionContext) {
    console.log('Congratulations, your extension "caythe" is now active!');

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
export function deactivate() {}
