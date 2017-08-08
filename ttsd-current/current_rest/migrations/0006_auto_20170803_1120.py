# -*- coding: utf-8 -*-
# Generated by Django 1.11.3 on 2017-08-03 11:20
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('current_rest', '0004_auto_20170801_1929'),
    ]

    operations = [
        migrations.AlterField(
            model_name='operationlog',
            name='operation_type',
            field=models.CharField(choices=[(b'LOAN_ADD', '\u589e\u52a0\u503a\u6743\u4fe1\u606f'), (b'LOAN_AUDIT', '\u5ba1\u6838\u503a\u6743\u4fe1\u606f'), (b'LOAN_REJECT', '\u9a73\u56de\u503a\u6743\u4fe1\u606f'), (b'LOAN_EDIT', '\u7f16\u8f91\u503a\u6743\u4fe1\u606f'), (b'REDEEM_AUDIT_PASS', '\u5ba1\u6838\u901a\u8fc7\u8d4e\u56de\u7533\u8bf7'), (b'REDEEM_AUDIT_REJECT', '\u9a73\u56de\u8d4e\u56de\u7533\u8bf7')], max_length=100, null=True),
        ),
    ]
