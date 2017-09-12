'use strict';
Object.defineProperty(exports, "__esModule", { value: true });
const vscode = require("vscode");
/**
 * Activates the extension.
 *
 * @param context must not be {@code null}
 */
function activate(context) {
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
exports.activate = activate;
/**
 * Deactivate the extension.
 */
function deactivate() { }
exports.deactivate = deactivate;
//# sourceMappingURL=extension.js.map