#!/system/bin/sh -x
test -x /system/xbin/su
if [ "$?" == 0 ] ; then 
    echo "already r00ted"
    exit 0
fi

echo "running from uid 0..."
echo "mount /system/xbin to memory..."
mount -t tmpfs none /system/xbin
echo "installing busybox and su..."
chmod 4755 /data/data/ru.meizu.m9.r00t/files/busybox
chmod 4755 /data/data/ru.meizu.m9.r00t/files/su
/data/data/ru.meizu.m9.r00t/files/busybox --install -s /system/xbin
/data/data/ru.meizu.m9.r00t/files/busybox cp /data/data/ru.meizu.m9.r00t/files/su /system/xbin/su
if [ "$?" == 0 ] ; then
    echo "done. now you have temporary root rights."
else
    echo "oops, something wrong."
fi
