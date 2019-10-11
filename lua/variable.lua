a = 5    -- ȫ�ֱ���
local b = 5 --�ֲ�����

function joke()
    c = 5                -- ȫ�ֱ���
    local d = 6          -- �ֲ�����
end

joke()
print(c,d);

do
    local a = 6
    b = 6
    print(a,b);
end

print(a,b)


--��ֵ���
a = "hello" .. "world"

x=2
a,b = 10,2*x

print(a,b)

a , b = b , a;  --lua会先计算右侧的值再赋值

print(a,b)

x,y,z = 0, 1
print(x,y,z)   --赋值不足的用空nil补


site={}
site["key"] = "hello world";
print(site["key"])
print(site.key)

