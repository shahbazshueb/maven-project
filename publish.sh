
#!/bin/bash

currentVersion="v"$(cat version.sbt | egrep -o '([0-9]|\.)+')


if [ "$1" == "-final" ]; then
    sbt -Dfinal release
else
    sbt release
    currentVersion=$currentVersion"-SNAPSHOT"
fi &&

git checkout mvn-repo &&
git reset --hard &&
git pull origin mvn-repo &&
rsync -av release/* mvn-repo/
git add mvn-repo/ &&
git commit -m $currentVersion &&
git push origin mvn-repo &&
git checkout master &&
sbt clean
rm -rf release

