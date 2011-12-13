#!/system/bin/sh -x

BASEDIR="/data/data/ru.meizu.m9.r00t/files"

#test -x /system/bin/su
test -x /sbin/su
if [ "$?" == 0 ] ; then 
    echo "already r00ted"
    exit 0
fi

echo "running from uid 0..."
"$BASEDIR"/busybox rm -r "$BASEDIR"/system

echo "prepare busybox and su..."
chmod 4755 "$BASEDIR"/busybox
chmod 4755 "$BASEDIR"/su

echo "remount / to rw mode..."
mount -w -o remount -t rootfs rootfs /

#echo "store original /system/bin to new path..."
#"$BASEDIR"/busybox mkdir -p "$BASEDIR"/system/bin
#"$BASEDIR"/busybox mount -o bind /system/bin "$BASEDIR"/system/bin
#
#
#echo "mount /system/bin into memory..."
#"$BASEDIR"/busybox mount -t tmpfs none /system/bin
#
#echo "link original /system/bin content to new location..."
#"$BASEDIR"/busybox ln -s "$BASEDIR"/system/bin/* /system/bin/

echo "installing busybox and su..."
chmod 755 /sbin
"$BASEDIR"/busybox --install -s /sbin
"$BASEDIR"/busybox cp "$BASEDIR"/su /sbin
    
echo "store original /system/etc to new path..."
"$BASEDIR"/busybox mkdir -p "$BASEDIR"/system/etc
"$BASEDIR"/busybox mount -o bind /system/etc "$BASEDIR"/system/etc

echo "mount /system/etc into memory..."
"$BASEDIR"/busybox mount -t tmpfs none /system/etc

echo "link original /system/etc content to new location..."
"$BASEDIR"/busybox ln -s "$BASEDIR"/system/etc/* /system/etc/

echo "check adfree files in /data..." 
test -f /data/data/hosts
if [ "$?" == 0 ] ; then
    "$BASEDIR"/busybox ln -s /data/data/hosts /system/etc/
    echo "change system /etc/hosts to adfree version..."
else
    echo "adfree is not installed or not configured properly."
fi

echo "remount / to ro mode..."
mount -r -o remount -t rootfs rootfs /

if [ "$?" == 0 ] ; then
    echo "done. now you have temporary root rights."
else
    echo "oops, something wrong."
fi
