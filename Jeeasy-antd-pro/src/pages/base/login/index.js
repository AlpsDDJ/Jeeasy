import {
  AlipayCircleOutlined,
  LockTwoTone,
  MailTwoTone,
  MobileTwoTone,
  TaobaoCircleOutlined,
  UserOutlined,
  WeiboCircleOutlined,
} from '@ant-design/icons'
import { Alert, Space, message, Tabs } from 'antd'
import React, { useState } from 'react'
import ProForm, { ProFormCaptcha, ProFormCheckbox, ProFormText } from '@ant-design/pro-form'
import { useIntl, FormattedMessage, history } from 'umi'
import { getFakeCaptcha } from '@/services/login'
import ACTION, { namespace } from '@/models/login/actions'
import container from "@/utils/container";
import { REFRESH_TOKEN_KEY, TOKEN_KEY } from "@/utils/Const";
import { getPageQuery } from "@/utils/utils";
import styles from './index.less'

const LoginMessage = ({ content }) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

const Index = (props) => {
  const { userLogin = {}, submitting } = props;
  const { status, type: loginType } = userLogin;
  const [type, setType] = useState('account');
  const intl = useIntl();

  const handleSubmit = (values) => {
    const { dispatch } = props;
    dispatch(ACTION.LOGIN, { ...values, type }).then(payload => {
      const { token, refreshToken } = payload.result
      localStorage.setItem(TOKEN_KEY, token);
      localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
      const urlParams = new URL(window.location.href);
      const params = getPageQuery();
      message.success('🎉 🎉 🎉  登录成功！');
      let { redirect } = params;

      if (redirect) {
        const redirectUrlParams = new URL(redirect);

        if (redirectUrlParams.origin === urlParams.origin) {
          redirect = redirect.substr(urlParams.origin.length);

          if (redirect.match(/^\/.*#/)) {
            redirect = redirect.substr(redirect.indexOf('#') + 1);
          }
        } else {
          window.location.href = '/';
          return;
        }
      }

      history.replace(redirect || '/');
    })
  };

  return (
    <div className={styles.main}>
      <ProForm
        initialValues={{
          autoLogin: true,
        }}
        submitter={{
          render: (_, dom) => dom.pop(),
          submitButtonProps: {
            loading: submitting,
            size: 'large',
            style: {
              width: '100%',
            },
          },
        }}
        onFinish={async (values) => {
          handleSubmit(values);
        }}
      >
        <Tabs activeKey={type} onChange={setType}>
          <Tabs.TabPane
            key="account"
            tab={intl.formatMessage({
              id: 'pages.login.accountLogin.tab',
              defaultMessage: '账户密码登录',
            })}
          />
          <Tabs.TabPane
            key="mobile"
            tab={intl.formatMessage({
              id: 'pages.login.phoneLogin.tab',
              defaultMessage: '手机号登录',
            })}
          />
        </Tabs>

        {status === 'error' && loginType === 'account' && !submitting && (
          <LoginMessage
            content={intl.formatMessage({
              id: 'pages.login.accountLogin.errorMessage',
              defaultMessage: '账户或密码错误（admin/ant.design)',
            })}
          />
        )}
        {type === 'account' && (
          <>
            <ProFormText
              name="username"
              fieldProps={{
                size: 'large',
                prefix: <UserOutlined className={styles.prefixIcon}/>,
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.username.placeholder',
                defaultMessage: '用户名: admin or user',
              })}
              initialValue="admin"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.username.required"
                      defaultMessage="请输入用户名!"
                    />
                  ),
                },
              ]}
            />
            <ProFormText.Password
              name="password"
              fieldProps={{
                size: 'large',
                prefix: <LockTwoTone className={styles.prefixIcon}/>,
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.password.placeholder',
                defaultMessage: '密码: ant.design',
              })}
              initialValue="123456"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.password.required"
                      defaultMessage="请输入密码！"
                    />
                  ),
                },
              ]}
            />
          </>
        )}

        {status === 'error' && loginType === 'mobile' && !submitting && (
          <LoginMessage content="验证码错误"/>
        )}
        {type === 'mobile' && (
          <>
            <ProFormText
              fieldProps={{
                size: 'large',
                prefix: <MobileTwoTone className={styles.prefixIcon}/>,
              }}
              name="mobile"
              placeholder={intl.formatMessage({
                id: 'pages.login.phoneNumber.placeholder',
                defaultMessage: '手机号',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.phoneNumber.required"
                      defaultMessage="请输入手机号！"
                    />
                  ),
                },
                {
                  pattern: /^1\d{10}$/,
                  message: (
                    <FormattedMessage
                      id="pages.login.phoneNumber.invalid"
                      defaultMessage="手机号格式错误！"
                    />
                  ),
                },
              ]}
            />
            <ProFormCaptcha
              fieldProps={{
                size: 'large',
                prefix: <MailTwoTone className={styles.prefixIcon}/>,
              }}
              captchaProps={{
                size: 'large',
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.captcha.placeholder',
                defaultMessage: '请输入验证码',
              })}
              captchaTextRender={(timing, count) =>
                timing
                  ? `${count} ${intl.formatMessage({
                    id: 'pages.getCaptchaSecondText',
                    defaultMessage: '获取验证码',
                  })}`
                  : intl.formatMessage({
                    id: 'pages.login.phoneLogin.getVerificationCode',
                    defaultMessage: '获取验证码',
                  })
              }
              name="captcha"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.captcha.required"
                      defaultMessage="请输入验证码！"
                    />
                  ),
                },
              ]}
              onGetCaptcha={async (mobile) => {
                const result = await getFakeCaptcha(mobile);

                if (result === false) {
                  return;
                }

                message.success('获取验证码成功！验证码为：1234');
              }}
            />
          </>
        )}
        <div
          style={{
            marginBottom: 24,
          }}
        >
          <ProFormCheckbox noStyle name="autoLogin">
            <FormattedMessage id="pages.login.rememberMe" defaultMessage="自动登录"/>
          </ProFormCheckbox>
          <a
            style={{
              float: 'right',
            }}
          >
            <FormattedMessage id="pages.login.forgotPassword" defaultMessage="忘记密码"/>
          </a>
        </div>
      </ProForm>
      <Space className={styles.other}>
        <FormattedMessage id="pages.login.loginWith" defaultMessage="其他登录方式"/>
        <AlipayCircleOutlined className={styles.icon}/>
        <TaobaoCircleOutlined className={styles.icon}/>
        <WeiboCircleOutlined className={styles.icon}/>
      </Space>
    </div>
  );
};

export default container(false, namespace, (obj) => {
  const { auth, loading } = obj
  return {
    userLogin: auth,
    submitting: loading.effects['auth/login'],
  }
})(Index);
