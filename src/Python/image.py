import cv2  # pip install opencv-python
import urllib.request
import numpy as np
import sys
import json
import imagehash  # pip install imagehash
from PIL import Image


# # S3 Object Key -> Convert -> IMAGE
def url_to_image(URL):
    resp = urllib.request.urlopen(URL)
    image = np.asarray(bytearray(resp.read()), dtype="uint8")
    image = cv2.imdecode(image, cv2.IMREAD_COLOR)

    return image


def main():
    url_list = json.loads(sys.argv[1])

    image_list = []
    hash_dict = {}

    # 사진 파일들을 image_list 에 추가.
    for i in range(len(url_list)):
        if (
            url_list[i]["download"].find(".PNG") != -1
            or url_list[i]["download"].find(".jpg") != -1
            or url_list[i]["download"].find(".png") != -1
        ):
            image_list.append(url_list[i]["download"])

    try:  # 해쉬 값들을 통해 비교.
        for image_url in image_list:
            image_data = url_to_image(image_url)
            image_gray = cv2.cvtColor(image_data, cv2.COLOR_BGR2GRAY)
            image_hash = imagehash.average_hash(Image.fromarray(image_gray))
            if image_hash in hash_dict:
                hash_dict[image_hash].append(image_url)
            else:
                hash_dict[image_hash] = [image_url]

        for urls in hash_dict.values():
            if len(urls) > 1:
                print("중복 이미지 그룹:")
                for url in urls:
                    print(url)
                print()
    except Exception as e:
        print(e)

    # try:
    #     for a in range(0, len(image_list)):
    #         image1 = url_to_image(image_list[a])
    #         image1g = cv2.cvtColor(image1, cv2.COLOR_BGR2GRAY)
    #         for b in range(0, len(image_list)):
    #             image2 = url_to_image(image_list[b])
    #             image2g = cv2.cvtColor(image2, cv2.COLOR_BGR2GRAY)
    #             if image1g.shape != image2g.shape:
    #                 continue

    #             (score, diff) = ssim(image1g, image2g, full=True)
    #             if (score) == 1.0:
    #                 print(image_list[a], image_list[b], " : 이미지 중복")
    # except Exception as e:
    #     print(e)


if __name__ == "__main__":
    main()
