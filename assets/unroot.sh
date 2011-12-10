#!/system/bin/sh -x
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/etc'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/fonts'
/system/bin/su -c '/data/data/ru.meizu.m9.r00t/files/busybox umount /system/bin' &&
echo "device unr00ted."
echo "to obtain r00t rights touch Get r00t."

