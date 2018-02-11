# Lean Launcher

by hundeva (https://github.com/hundeva/Lean-Launcher)

This launcher is based on the o-mr1 version of Amir Zaidi's Rootless Pixel Launcher: https://github.com/amirzaidi/Launcher3/tree/o-mr1?files=1

The goal of Lean Launcher is to preserve the feeling of the Pixel Launcher, but to provide a few extra options for customization.

## Features

Everything, that is available in Rootless Pixel Launcher, with some added options, such as:

- option to hide "At A Glance"
- option to hide bottom search bar on your home screen
- option for extra bottom padding, some devices may need it when bottom search bar is hidden
- option to force colored G icon on bottom search bar
- option to hide app search bar at the top of your app drawer
- option to disable the spring effect when over scrolling your app drawer
- option to make status bar transparent
- option to lock your desktop, to prevent accidental changes (and to be able to use shortcuts better)
- double tap to lock on home screen, this is a secure lock, meaning you will need to provide PIN if you have set it up, fingerprint is not enough
- option to force dark or light theme, not just based on your wallpaper
- option to change default grid options (rows, column, hotseat icons, from 3 up to 7)
- hide apps from your drawer (searching apps works for hidden apps as well)

## Package name

In order for "At A Glance" to work, the package name has to be the same as the original Pixel Launcher. If I understand it correctly, the Google app sends an explicit broadcast, which can only be received by the Pixel Launcher package. For this reason, there is no Play Store release so far.

There is a possible workaround, to create a companion app with the Pixel Launcher package name, which will receive and forward the broadcasts, but that is not planned for now, maybe if there is enough interest.

## Note for Pixel users

A separate package is planned, which is most likely going to be uploaded to the Play Store. This version is planned to have two different companion apps, one for Pixel users, that will enable the swipe to Google feed feature, one for non Pixel users, which will enable both the feed, both the At A Glance feature.

## Bug reports

Open an issue, with as much details as you can give, "the launcher crashed" is not going to cut it. Reproduction steps, adb logs, anything that you can provide helps.

## Feature requests

Open an issue, I will try to respond and see if it is something that I'm willing to do. No promises though. :)

## Contributions

Contributions are welcome, take a look at the open issues here: https://github.com/hundeva/Lean-Launcher/issues

If you feel like you can solve one, while not modifying the base launcher code too much, create a fork, and send a pull request when you are done.
