
#!/bin/bash

if [[ `git status --porcelain` ]]; then
  echo "clean working directory"
  exit 0
fi

versionText=$(cat build.sbt | grep "version := ")

if [ -z "$versionText" ]; then
  echo "version not properly mentioned in build.sbt"
  exit 0
fi

if [[ `git log --all --grep="$versionText" origin/mvn-repo` ]]; then
  echo "version already published on github mvn-repo branch"
  exit 0
fi

sbt publish
git fetch
git checkout mvn-repo
git reset --hard
git pull origin mvn-repo
git add mvn-repo
git commit -m "$versionText"
git push origin mvn-repo
git checkout master
