<template>
  <div id="app">
    <div id="terminal"></div>
  </div>
</template>

<script>
  import jQuery from 'jquery.terminal';
  import 'jquery.terminal/css/jquery.terminal.min.css';

  /**
   * 网址列表
   */
  const webList = {};

  export default {
    name: 'App',
    components: {},
    mounted() {
      jQuery(function ($, ud) {
        const terminal = $('#terminal').terminal([function (command) {
          if (command !== '') {
            var result = window.eval(command);
            if (result !== ud) {
              this.echo(String(result));
            }
          }
        }, {
          help(key) {
            console.log(key)
            this.echo(`add name webLink 添加网址`)
            this.echo(`list 查看收藏夹`)
            this.echo(`open name 打开网站`)
          },
          add(key, link) {
            if (key && link) {
              webList[key] = link;
            }
          },
          open(key) {
            window.open(webList[key]);
          },
          list() {
            this.echo(JSON.stringify(webList));
          }
        }], {
          greetings: '👨 ‍father - 万能控制台',
          name: 'father',
          prompt: '> ',
        });
        terminal.echo(`支持功能：\n 1. 网页收藏夹 \n 输入 help <序号> 查看用法`);
      });
    }
  }
</script>

<style>
  body {
    margin: 0 !important;
  }

  #app {

  }

  #terminal {
    height: 100vh;
    padding: 32px;
  }

  .cmd, .cmd div, .cmd span, .terminal, .terminal-output > :not(.raw) a, .terminal-output > :not(.raw) div, .terminal-output > :not(.raw) span {
    font-size: 18px !important;
    line-height: 18px !important;
  }

  .terminal .terminal-output > div:not(.raw) div {
    margin-bottom: 10px;
  }
</style>
