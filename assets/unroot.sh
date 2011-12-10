#!/system/bin/sh -x
BASEDIR="/data/data/ru.meizu.m9.r00t"
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/etc'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /data/data/ru.meizu.m9.r00t/files/system/etc'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/fonts'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /data/data/ru.meizu.m9.r00t/files/system/fonts'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /data/data/ru.meizu.m9.r00t/files/system/bin'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/bin' &&
echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

