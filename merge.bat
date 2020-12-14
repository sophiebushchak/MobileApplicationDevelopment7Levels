set /p repository="Enter Repository "
git remote add -f %repository% https://github.com/sophiebushchak/%repository%
git merge %repository%/master --allow-unrelated-histories
mkdir %repository%
pause