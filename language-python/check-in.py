import requests

url = "https://glados.rocks/api/user/checkin"


def pre():
    data = {"token": "glados.network"}
    head = {
        'cookie': '__stripe_mid=ac58181c-00d1-48d5-96c7-59275156e9f907d321; koa:sess=eyJ1c2VySWQiOjIxMDc1OCwiX2V4cGlyZSI6MTcwMzQ2NTcwMjk5MywiX21heEFnZSI6MjU5MjAwMDAwMDB9; koa:sess.sig=PyxyswyJSfVH_4IpUYdwXi4be5E'}
    req = requests.post(url, data=data, headers=head)
    print(req.text)


if __name__ == "__main__":
    pre()
