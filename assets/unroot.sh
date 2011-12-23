#!/system/bin/sh -x
BASEDIR="/data/data/ru.meizu.m9.r00t/files"

su -c "mount -w -o remount -t rootfs rootfs /"
su -c "$BASEDIR/busybox umount -l /system/etc"
su -c "$BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/etc"
su -c "$BASEDIR/busybox umount -lf /system/fonts"

test -f /data/local/fonts/DroidSansFallback.ttf
if [ "$?" == 0 ] ; then
    su -c "$BASEDIR/busybox killall com.android.launcher"
    su -c "am start -n com.android.launcher/com.android.launcher2.Launcher"
    su -c "$BASEDIR/busybox killall com.android.systemui"
    su -c "am startservice -n com.android.systemui/.statusbar.StatusBarService"
    su -c "$BASEDIR/busybox killall com.android.mms"
    su -c "$BASEDIR/busybox killall com.android.phone"
    su -c "$BASEDIR/busybox rm -rf /data/local/fonts"
fi

su -c "$BASEDIR/busybox rm -f /sbin/su ; mount -r -o remount -t rootfs rootfs /"

#su -c "$BASEDIR/busybox umount -l /system/bin; $BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/bin"

echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

