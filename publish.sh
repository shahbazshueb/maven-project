
#!/bin/bash

currentVersion=$(cat build.sbt | grep "version := " | egrep -o '([0-9]|\.)+')

if ! [[ `git branch | grep "\* master$"` ]]; then
  echo "new artifact can only be published from master branch"
  exit 1
fi

if [[ `git status --porcelain` ]]; then
  echo "clean working directory required to publish artifact"
  exit 1
fi

if [ -z "$currentVersion" ]; then
  echo "version not properly mentioned in build.sbt"
  exit 1
fi

if [[ `git fetch && git log --all --grep="version $currentVersion" origin/mvn-repo` ]]; then
  echo "version already published on github mvn-repo branch"
  exit 1
fi

sbt publish &&
git checkout mvn-repo &&
git reset --hard &&
git pull origin mvn-repo &&
git add mvn-repo &&
git commit -m "version $currentVersion" &&
git push origin mvn-repo &&
git checkout master &&
sbt clean

