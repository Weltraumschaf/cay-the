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
    let controller = new WordCounterController(wordCounter);
    context.subscriptions.push(wordCounter);
    context.subscriptions.push(controller);

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

class WordCounterController {

    private _wordCounter: WordCounter;
    private _disposable: vscode.Disposable;

    constructor(wordCounter: WordCounter) {
        this._wordCounter = wordCounter;

        // subscribe to selection change and editor activation events
        let subscriptions: vscode.Disposable[] = [];
        vscode.window.onDidChangeTextEditorSelection(this._onEvent, this, subscriptions);
        vscode.window.onDidChangeActiveTextEditor(this._onEvent, this, subscriptions);

        // update the counter for the current file
        this._wordCounter.updateWordCount();

        // create a combined disposable from both event subscriptions
        this._disposable = vscode.Disposable.from(...subscriptions);
    }

    dispose() {
        this._disposable.dispose();
    }

    private _onEvent() {
        this._wordCounter.updateWordCount();
    }
}