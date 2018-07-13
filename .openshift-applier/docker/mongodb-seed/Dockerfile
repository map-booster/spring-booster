FROM openshift3/rhel7:7.5
USER root

COPY mongodb-org-3.6.repo /etc/yum.repos.d/mongodb-org-3.6.repo
RUN yum --disablerepo=rhel-7-server-htb-rpms install -y mongodb-org-tools-3.6.5 mongodb-org-shell-3.6.5;

COPY data/ /tmp/data

USER 1001
