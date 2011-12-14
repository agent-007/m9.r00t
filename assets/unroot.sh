#!/system/bin/sh -x
BASEDIR="/data/data/ru.meizu.m9.r00t/files"

su -c "mount -w -o remount -t rootfs rootfs /"
su -c "$BASEDIR/busybox umount -l /system/etc"
su -c "$BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/etc"
su -c "$BASEDIR/busybox rm -f /sbin/su ; mount -r -o remount -t rootfs rootfs /"

#su -c "$BASEDIR/busybox umount -l /system/bin; $BASEDIR/busybox umount -l /data/data/ru.meizu.m9.r00t/files/system/bin"

echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

