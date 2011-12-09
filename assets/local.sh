#!/system/bin/sh -x

test -x /system/bin/su
if [ "$?" == 0 ] ; then 
    echo "already r00ted"
    exit 0
fi

echo "running from uid 0..."

echo "prepare busybox and su..."
chmod 4755 /data/data/ru.meizu.m9.r00t/files/busybox
chmod 4755 /data/data/ru.meizu.m9.r00t/files/su

echo "store original /system/bin to new path..."
/data/data/ru.meizu.m9.r00t/files/busybox mkdir -p /data/data/ru.meizu.m9.r00t/files/system/bin
/data/data/ru.meizu.m9.r00t/files/busybox mount -o bind /system/bin /data/data/ru.meizu.m9.r00t/files/system/bin

echo "mount /system/bin into memory..."
/data/data/ru.meizu.m9.r00t/files/busybox mount -t tmpfs none /system/bin

echo "link original /system/bin content to new location..."
/data/data/ru.meizu.m9.r00t/files/busybox ln -s /data/local/system/bin/* /system/bin/

echo "installing busybox and su..."
/data/data/ru.meizu.m9.r00t/files/busybox --install -s /system/bin
/data/data/ru.meizu.m9.r00t/files/busybox cp /data/data/ru.meizu.m9.r00t/files/su /system/xbin/su

if [ "$?" == 0 ] ; then
    echo "done. now you have temporary root rights."
else
    echo "oops, something wrong."
fi
