package baeldung.synchronizer.Phaser;

/**
 * Phaser 
 * The Phaser allows us to build logic in which threads need to wait on the barrier 
 * 		before going to the next step of execution.
 * We can coordinate multiple phases of execution, reusing a Phaser instance for each program phase. 
 * Each phase can have a different number of threads waiting for advancing to another phase.
 * 1. 整体任务可分多阶段分别执行；
 * 2. 不同阶段可绑定不同的线程数；
 * 3. Phaser在不同阶段可重用。
 */
