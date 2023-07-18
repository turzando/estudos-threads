# Virtual Threads #

### O que são?

São abstrações fornecidas pela JVM para permitir que um programa Java execute um grande número de Threads Virtuais, mesmo que o Sistema Operacional imponha um limite menor de "Threads Nativas".

~~~
Threads Nativas são as Threads gerenciadas pelo sistema operacional. 
Quando há uma instância da classe Thread (new Thread) em Java, você está criando uma nova Thread nativa.

Obs: a classe 'Thread' em Java é uma abstração que fornece uma interface (Thread de Plataforma) para trabalhar com Threads nativas.  
~~~

### Como funcionam 

imagem aqui

A JVM mantém um pool de threads de plataforma, criados e mantidos por um dedicado 'ForkJoinPool'. Inicialmente, o número de threads da plataforma é igual ao número de núcleos da CPU e não pode aumentar mais do que 256.

Para cada Thread Virtual criada, a JVM agenda sua execução em um Thread de plataforma, copiando temporariamente o pedaço de pilha da vThread para a pilha da Thread de plataforma. Dissemos que o thread da plataforma se torna o thread portador do thread virtual.

Agora rode algumas vezes o método run() da classe VirtualThreadExample e repare em como os "worker" podem estar sendo alterados dentro da mesma tarefa:

IMG AQUI

Interessante, né?! Bom, isso acontece pois quando uma tarefa é submetida como uma thread virtual, a JVM pode decidir atribuí-la a um thread de plataforma específica para execução. No entanto, como as threads virtuais são independentes das threads nativas e são gerenciadas pelo ForkJoinPool, o sistema pode criar e destruir threads virtuais conforme necessário, distribuindo as tarefas entre elas.

Isso significa que, em determinados momentos, a mesma tarefa pode ser atribuída a diferentes threads portadoras em execuções diferentes. Esse comportamento pode ser resultado do balanceamento de carga ou da otimização de desempenho realizada pelo ForkJoinPool. Isso será explicado melhor no próximo tópico.

~~~
O ForkJoinPool é uma classe do framework de concorrência do Java que implementa um pool de threads otimizado para tarefas baseadas no princípio de divisão e conquista (fork-join), quebrando tarefas em subtarefas menores e independentes, que podem ser executadas em paralelo.
É construído em torno do conceito de work-stealing (roubo de trabalho). Se uma thread termina sua parte da tarefa e vê que outra thread ainda tem trabalho para fazer, ela pode roubar um pouco desse trabalho e ajudar.
~~~

### Cooperative Scheduling 

Em Java, threads virtuais implementam agendamento cooperativo. Quando atinge uma operação de bloqueio a Thread Virtual é "desmontada" da Thread portadora e fica em espera, fornecendo espaço para que outra Thread Virtual seja "montada" e tenha sua execução.

Podemos verificar esse comportamento definindo uma operação de bloqueio causado por um loop infinito.

Agora adicione as seguintes propriedades do sistema:

~~~
-Djdk.virtualThreadScheduler.parallelism=1
-Djdk.virtualThreadScheduler.maxPoolSize=1
-Djdk.virtualThreadScheduler.minRunnable=1
~~~

E rode o método *runWithCooperativeScheduling*, onde será possível ver a troca de tarefas executada no operador, pelo fato da tarefa de workingForever estar executando infinitamente.

Agora altere as propriedades do sistema para:
~~~
-Djdk.virtualThreadScheduler.parallelism=2
-Djdk.virtualThreadScheduler.maxPoolSize=2
-Djdk.virtualThreadScheduler.minRunnable=2
~~~

Rodando o método runWithoutCooperativeScheduling será possível ver que as execuções serão executados em operadores diferentes, pois agora serão agendadas em cada operador para serem executadas simultaneamente.

### Pinned Virtual Threads 

Foi dito anteriormente que sempre que ocorre uma operação de bloqueio a thread virtual é desmontada do operador a fim de outra thread virtual ser executada, porém há alguns casos que essa operação de bloqueio não desmonta a thread virtual do operador.

1 - Quando ocorre um bloqueio intrínseco (bloco de código ou método synchronized, por exemplo).
2 - Quando chama um método nativo ou uma função estrangeira (ou seja, uma chamada para uma biblioteca nativa usando JNI).

Por exemplo, vamos simular que um funcionário precisa ir ao banheiro. A casa de banho tem apenas um vaso sanitário, então o vaso sanitário deve ser sincronizado:

Altere as propriedades do sistema para:
~~~
-Djdk.virtualThreadScheduler.parallelism=1
-Djdk.virtualThreadScheduler.maxPoolSize=2
-Djdk.virtualThreadScheduler.minRunnable=1
~~~

Rodando o método runWithPinnedVirtualThreads é possível ver que o as tarefas irão ser executadas em operadores diferentes, pois uma tarefa está passando por "bloqueado". Ou seja, a gente "fixou" a nossa vThread em um operador para que aguardasse o bloqueio ser desfeito.