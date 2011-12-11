#!/system/bin/sh -x
BASEDIR="/data/data/ru.meizu.m9.r00t/files"
chmod 4755 "$BASEDIR/su"

su -c "$BASEDIR/busybox umount -l /system/etc"
su -c "$BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/etc"

su -c "$BASEDIR/busybox umount -l /system/bin; $BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/bin"

echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

