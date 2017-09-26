#!/bin/sh

while getopts "e:" OPT
do
  case $OPT in
    e ) FLG_ENV="TRUE" ; VALUE_ENV="$OPTARG" ;;
    * ) echo "Usage: $CMDNAME [-e {local, prod}]" 1>&2
        exit 1 ;;
  esac
done

if [ "$FLG_ENV" = "TRUE" ]; then
	if [ "$VALUE_ENV" = "local" ]; then
		docker-compose up -d
	elif [ "$VALUE_ENV" = "prod" ]; then
		docker-compose -f docker-compose.prod.yml up -d
		:
	else
		echo "-e [local, prod]" 1>&2
		exit 1
	fi
fi
