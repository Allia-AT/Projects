# Generated by Django 4.2.5 on 2023-11-07 16:27

import app_album_viewer.models
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app_album_viewer', '0003_albumcomments_album'),
    ]

    operations = [
        migrations.AlterField(
            model_name='album',
            name='release_date',
            field=models.DateTimeField(validators=[app_album_viewer.models.validate_release_date]),
        ),
    ]
