#!/system/bin/sh -x
BASEDIR="/data/data/ru.meizu.m9.r00t/files"
chmod 4755 "$BASEDIR/su"

"$BASEDIR/su" -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/etc'
"$BASEDIR/su" -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /data/data/ru.meizu.m9.r00t/files/system/etc'

"$BASEDIR/su" -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/bin'
"$BASEDIR/su" -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /data/data/ru.meizu.m9.r00t/files/system/bin'

echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

