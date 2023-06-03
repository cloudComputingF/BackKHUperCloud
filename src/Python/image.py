# For 중복 이미지 처리
photo_list = []

"""
S3 로 파일 불러오기.



"""
# pip install opencv-python
import cv2
from skimage.metrics import structural_similarity as ssim

import urllib.request
import numpy as np

""" => GET S3 Object URL 
def create_presigned_url(bucket_name, bucket_key, expiration=3600, signature_version=s3_signature['v4']):

    s3_client = boto3.client('s3',
                         aws_access_key_id="AWS_ACCESS_KEY",
                         aws_secret_access_key="AWS_SECRET_ACCESS_KEY",
                         config=Config(signature_version=signature_version),
                         region_name='ap-south-1'
                         )
    try:
        response = s3_client.generate_presigned_url('get_object',
                                                Params={'Bucket': bucket_name,
                                                        'Key': bucket_key},
                                                ExpiresIn=expiration)
        print(s3_client.list_buckets()['Owner'])
        for key in s3_client.list_objects(Bucket=bucket_name,Prefix=bucket_key)['Contents']:
            print(key['Key'])
    except ClientError as e:
        logging.error(e)
        return None
        # The response contains the presigned URL
    return response
"""


# S3 Object URL -> Convert -> IMAGE
def url_to_image(URL):
    resp = urllib.request.urlopen(URL)
    image = np.asarray(bytearray(resp.read()), dtype="unint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image


image = url_to_image("https://khufcloud.s3.ap-northeast-2.amazonaws.com/test.png")
print(image)


"""
generated_signed_url = create_presigned_url(you_bucket_name, bucket_key, 
seven_days_as_seconds, s3_signature['v4'])
print(generated_signed_url)
image_complete = url_to_image(generated_signed_url)

"""
