
#!/bin/bash

currentVersion=$(cat build.sbt | grep "version := ")

if ! [[ `git branch | grep "\* master$"` ]]; then
  echo "new artifact can only be published from master branch"
  exit 0
fi

if [[ `git status --porcelain` ]]; then
  echo "clean working directory"
  exit 0
fi

if [ -z "$currentVersion" ]; then
  echo "version not properly mentioned in build.sbt"
  exit 0
fi

if [[ `git log --all --grep="$currentVersion" origin/mvn-repo` ]]; then
  echo "version already published on github mvn-repo branch"
  exit 0
fi

sbt publish
git fetch
git checkout mvn-repo
git reset --hard
git pull origin mvn-repo
git add mvn-repo
git commit -m "$currentVersion"
git push origin mvn-repo
git checkout master
