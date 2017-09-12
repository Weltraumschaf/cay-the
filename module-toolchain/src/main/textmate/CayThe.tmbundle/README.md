# CayThe TextMate Bundle

This is the CayThe language TextMate bundle

## Authors

- Sven Strittmatter [@weltraumschaf](https://github.com/Weltraumschaf)

## License

- [The Beer-Ware-License](http://www.weltraumshcaf.de/bee-ware-license.txt)

## Install

The quickest way to install the bundle is  via the command line. If you have Git
installed, you'll  probably want to install  with Git. With or  without, you can
simply  copy and  paste each  line  one by  one into  the Terminal  instructions
(lifted from [drnic](http://github.com/drnic/ruby-on-rails-tmbundle)):

### Install with Git

    $> cd ~/Library/Application\ Support/TextMate/Bundles
    $> git clone https://github.com/Weltraumschaf/cay-the.git "Caythe.tmbundle"
    $> osascript -e 'tell app "TextMate" to reload bundles'

### Install without Git:

    $> cd ~/Library/Application\ Support/TextMate/Bundles
    $> wget https://github.com/Weltraumschaf/cay-the/tarball/master
    $> tar zxf master
    $> rm -rf master
    $> mv weltraumschaf-caythe-tmbundle* "Caythe.tmbundle"
    $> osascript -e 'tell app "TextMate" to reload bundles'

## Download

If you'd like to avoid the command line altogether, you can download the bundle and install it:

1. download the zip from [https://github.com/Weltraumschaf/cay-the/zipball/master](https://github.com/Weltraumschaf/caythe-tmbundle/zipball/master)
2. find the zip file on your local machine and double-click to unzip it
3. change the file name from *weltraumschaf-caythe-tmbundle-random_sequence* to *Caythe.tmbundle* (with a dot rather than a hyphen).
4. double-click the *Caythe.tmbundle* file
5. open TextMate and select the following menu item: *Bundles > Bundle Editor > Reload Bundles*
6. show the Bundle Editor (*Bundles > Bundle Editor > Show Bundle Editor*)
7. scroll through the list of bundles to confirm that the bundle has been properly installed

## Known Issues & Todos

Nothing here yet.
