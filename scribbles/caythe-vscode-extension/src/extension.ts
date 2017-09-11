'use strict';
import * as vscode from 'vscode';

/**
 * Activates the extension.
 *
 * @param context must not be {@code null}
 */
export function activate(context: vscode.ExtensionContext) {
    console.log('Congratulations, your extension "caythe" is now active!');
    let wordCounter = new WordCounter();

    let sayHello = vscode.commands.registerCommand('extension.sayHello', () => {
        wordCounter.updateWordCount();

        var editor = vscode.window.activeTextEditor;
        if (!editor) {
            return; // No open text editor
        }

        var selection = editor.selection;
        var text = editor.document.getText(selection);

        // Display a message box to the user
        vscode.window.showInformationMessage('Selected characters: ' + text.length);
    });

    context.subscriptions.push(wordCounter);
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
export function deactivate() {}

class WordCounter {

    private _statusBarItem: vscode.StatusBarItem;

    public updateWordCount() {
        // Create as needed
        if (!this._statusBarItem) {
            this._statusBarItem = vscode.window.createStatusBarItem(vscode.StatusBarAlignment.Left);
        }

        // Get the current text editor
        let editor = vscode.window.activeTextEditor;
        if (!editor) {
            this._statusBarItem.hide();
            return;
        }

        let doc = editor.document;

        // Only update status if an Markdown file
        if (doc.languageId === "markdown") {
            let wordCount = this._getWordCount(doc);

            // Update the status bar
            this._statusBarItem.text = wordCount !== 1 ? `${wordCount} Words` : '1 Word';
            this._statusBarItem.show();
        } else {
            this._statusBarItem.hide();
        }
    }

    public _getWordCount(doc: vscode.TextDocument): number {
        let docContent = doc.getText();

        // Parse out unwanted whitespace so the split is accurate
        docContent = docContent.replace(/(< ([^>]+)<)/g, '').replace(/\s+/g, ' ');
        docContent = docContent.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
        let wordCount = 0;
        if (docContent != "") {
            wordCount = docContent.split(" ").length;
        }

        return wordCount;
    }

    dispose() {
        this._statusBarItem.dispose();
    }
}